package com.frxs.wmspick.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.frxs.common_base.utils.CommonUtils;
import com.frxs.common_base.utils.SystemUtils;
import com.frxs.common_base.utils.ToastUtils;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.adapter.ProductAdapter;
import com.frxs.wmspick.model.CompletePickOrder;
import com.frxs.wmspick.model.PickingGroup;
import com.frxs.wmspick.model.PickingOrder;
import com.frxs.wmspick.model.PickingOrderCount;
import com.frxs.wmspick.model.UserInfo;
import com.frxs.wmspick.rest.model.AjaxParams;
import com.frxs.wmspick.rest.model.ApiRequest;
import com.frxs.wmspick.rest.model.ApiResponse;
import com.frxs.wmspick.rest.service.SimpleCallback;
import com.frxs.wmspick.widget.ChoosePathDialog;
import com.frxs.wmspick.widget.UpdateMoneyDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT;

/**
 * Created by Chentie on 2018/9/12.
 */

public class StartPickActivity extends MyBaseActivity {

    private final static int loadThreshold = 1; //预加载阀值（表示剩下多少个订单的时候进行预加载）
    private TextView titleTv;
    private TextView actionBackTv;
    private RecyclerView reView;
    private LinearLayout finishPick;
    private ProductAdapter reAdapter;
    // 当前拣货订单
    private PickingOrder pickingOrder;
    private TextView tvStoreSore;
    private TextView tvProductSum;
    private TextView tvProductLines;
    private TextView actionRightTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pick);
        //获取布局参数
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        actionBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finishPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isFastDoubleClick()) {
                    return;
                }
                showProgressDialog();
                if (pickingOrder != null) {
                    for (PickingOrder.ItemsBean item : pickingOrder.getItems()) {
                        if (!item.isPick()) {
                            dismissProgressDialog();
                            UpdateMoneyDialog.getInstance("还有商品未完成拣货").show(getSupportFragmentManager(),"no_pick");
                            return;
                        }
                    }
                }
                finishPicking();
            }
        });
        actionRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PickingOrder> historyOrders = (ArrayList<PickingOrder>)MyApplication.getInstance().getmHistoryOrders();
                if (historyOrders.size() <= 0) {
                    UpdateMoneyDialog.getInstance("没有历史拣货单").show(getSupportFragmentManager(),"no_pick");
                    return;
                }
                ChoosePathDialog.getInstance(null, historyOrders).show(getSupportFragmentManager(), "order_list");
            }
        });

        reAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.llBg: {
                        PickingOrder.ItemsBean item = (PickingOrder.ItemsBean) adapter.getItem(position);
                        item.setPick(!item.isPick());
                        adapter.notifyDataSetChanged();

                        initPickingGoodsItemView();
                        break;
                    }
                    case R.id.tvProductNum:
//                        UpdateMoneyDialog.getInstance(String.valueOf(item.getProductNum())).show(getSupportFragmentManager(), "product");
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    protected void initView() {
        titleTv = findViewById(R.id.titleTv);
        actionBackTv = findViewById(R.id.actionBackTv);
        tvStoreSore = findViewById(R.id.tv_store_sore);
        reView = findViewById(R.id.rcView);
        reView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        reAdapter = new ProductAdapter(R.layout.item_product, this, "pick");
        reView.setAdapter(reAdapter);
        reAdapter.setEmptyView(R.layout.layout_empty, (ViewGroup) reView.getParent());
        reAdapter.openLoadAnimation(SLIDEIN_LEFT );
        finishPick = findViewById(R.id.finishPick);
        tvProductSum = findViewById(R.id.tv_product_sum);
        tvProductLines = findViewById(R.id.tv_product_lines);
        actionRightTv = findViewById(R.id.actionRightTv);
        actionRightTv.setVisibility(View.VISIBLE);
        actionRightTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_history, 0);
    }

    @Override
    protected void initData() {
        MyApplication instance = MyApplication.getInstance();
        List<PickingOrder> pickingOrders = instance.getPickingOrders();
        if(pickingOrders.size() > 0) {
            pickingOrder = pickingOrders.get(0);
            reloadRecyclerView();
        } else {
            showProgressDialog();
            getPickingOrder(MyApplication.getInstance().getStartDateStr(),instance.getPickingGroup().getPGroupID(),
                    instance.getPickingGroup().getStationID());
        }
    }

    public void getPickingOrder(final String deliveryTime, final int pGroupId, final int stationId) {
        // 获取拣货类型
        PickingGroup pickingGroup = MyApplication.getInstance().getUserInfo().getPickingGroup();
        String pickType = pickingGroup.getPickType();
        // 封装请求参数
        ApiRequest params = new ApiRequest(this);
        params.put("DeliveryTime", deliveryTime);
        params.put("PGroupId", pGroupId);
        params.put("StationId", stationId);
        params.put("UserID", getUserID());
        params.put("UserName", getUserName());
        params.put("WID", getWID());
        params.put("OpAreaID", getOpAreaID());
        // 根据拣货类型请求拣货单
        switch (pickType) {
            case "SUM_SHOP": {//按门店拣货
                getStorePickingOrders(params);
            }
                break;
            case "SUM_LINE": {//按线路拣货
                getLinePickingOrders(params);
            }
                break;
            case "SUM_PRODUCT": {//按商品拣货
                getProductPickingOrders(params);
            }
                break;
            default:
                break;
        }

    }

    /**
     * 获取商品拣货单
     */
    private void getProductPickingOrders(AjaxParams params) {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        getService().GetProductPickingOrders(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<List<PickingOrder>>>() {
            @Override
            public void onResponse(ApiResponse<List<PickingOrder>> result, int code, String msg) {
                updatePickingOrderInfo(result);
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PickingOrder>>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 获取线路拣货单
     */
    private void getLinePickingOrders(AjaxParams params) {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        getService().GetLinePickingOrders(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<List<PickingOrder>>>() {
            @Override
            public void onResponse(ApiResponse<List<PickingOrder>> result, int code, String msg) {
                updatePickingOrderInfo(result);
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PickingOrder>>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 获取门店拣货单
     */
    private void getStorePickingOrders(AjaxParams params) {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        getService().GetPickingOrders(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<List<PickingOrder>>>() {
            @Override
            public void onResponse(ApiResponse<List<PickingOrder>> result, int code, String msg) {
                updatePickingOrderInfo(result);
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PickingOrder>>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 更新拣货单信息
     * @param result
     */
    private void updatePickingOrderInfo(ApiResponse<List<PickingOrder>> result) {
        dismissProgressDialog();
        if (result.isSuccessful()) {
            List<PickingOrder> mPickingOrders = MyApplication.getInstance().getPickingOrders();
            if (result.getData() != null) {
                List<PickingOrder> pickingOrders = result.getData();
                if (pickingOrders.size() > 0) {
                    if (mPickingOrders!= null && mPickingOrders.size() > 0) {
                        syncProductList(mPickingOrders, pickingOrders);
                        pickingOrder = mPickingOrders.get(0);
                    } else {
                        mPickingOrders.addAll(pickingOrders);
                        pickingOrder = mPickingOrders.get(0);
                        reloadRecyclerView();
                    }
                } else {
                    if (mPickingOrders.size() <= 0) {
                        UpdateMoneyDialog.getInstance("暂无拣货单，请回到首页").show(getSupportFragmentManager(), "info");
                    }
                }
            } else {
                if (mPickingOrders.size() <= 0) {
                    UpdateMoneyDialog.getInstance("暂无拣货单，请回到首页").show(getSupportFragmentManager(), "info");
                }
            }

        } else {
            if (result.isAuthenticationFailed()) {
                ToastUtils.show(StartPickActivity.this, getString(R.string.authentication_failed));
                reLogin();
            } else {
                UpdateMoneyDialog.getInstance(result.getInfo()).show(getSupportFragmentManager(), "info");
            }
        }
    }

    private void syncProductList(List<PickingOrder> oldOrderList, List<PickingOrder> newOrderList) {
        HashMap<String, PickingOrder> localProductsHashMap = new HashMap<String, PickingOrder>();
        if (null != oldOrderList) {
            for (PickingOrder localProduct: oldOrderList) {
                localProductsHashMap.put(localProduct.getPickID(), localProduct);
            }
        }

        if (localProductsHashMap.size() > 0) {
            for (PickingOrder item : newOrderList) {
                PickingOrder localProduct = localProductsHashMap.get(item.getPickID());
                if (null == localProduct) {
                    oldOrderList.add(item);
                }
            }
        }
    }

    private void reloadRecyclerView() {
        updateOrderData(pickingOrder);
        reAdapter.setNewData(pickingOrder.getItems());
        initPickingGoodsItemView();
    }

    /**
     * 设置最新数据
     */
    private void updateOrderData(PickingOrder order) {
        PickingGroup pickingGroup = MyApplication.getInstance().getUserInfo().getPickingGroup();
        switch (pickingGroup.getPickType()) {
            case "SUM_SHOP": {
                titleTv.setText(order.getLineName() + " - " + order.getStoreNo() + "-" + order.getStoreName());
                tvStoreSore.setText(order.getSendCardNo());
            }
            break;
            case "SUM_LINE": {
                titleTv.setText(order.getLineName());
                tvStoreSore.setVisibility(View.GONE);
            }
            break;
            case "SUM_PRODUCT": {
                titleTv.setText(order.getProductName() + "\n" + order.getSKUContent());
                tvStoreSore.setVisibility(View.GONE);
            }
            break;
            default:
                break;
        }
        // 商品总数量
        int sum = 0;
        for (PickingOrder.ItemsBean i : order.getItems()) {
            sum += i.getBuyQty();
        }
        tvProductSum.setText("商品总数：" + sum + " " + String.format(getString(R.string.product_lines), String.valueOf(order.getItems().size())));
        PickingOrderCount pickOrderSum = MyApplication.getInstance().getPickOrderSum();
        tvProductLines.setText("拣货单总数：" + pickOrderSum.getTotalCnt() + " 已拣数：" + pickOrderSum.getFinishedCnt());
    }


    /**
     * 跳转到第一个未拣的商品
     */
    private void initPickingGoodsItemView() {
        for (int i = 0; i < pickingOrder.getItems().size(); i++) {
            boolean pick = pickingOrder.getItems().get(i).isPick();
            if (!pick) {
                if (i<=1) {
                    // 跳转到未拣货的Item并初始化底部商品栏
                    reView.scrollToPosition(0);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) reView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(0, 0);
                    break;
                } else {
                    reView.scrollToPosition(i-2);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) reView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(i-2, 0);
                    break;
                }
            } else if (i == pickingOrder.getItems().size() - 2) {
                reView.scrollToPosition(i-2);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) reView.getLayoutManager();
                mLayoutManager.scrollToPositionWithOffset(i-2, 0);
            }
        }
    }

    /**
     * 完成拣货
     */
    private void finishPicking() {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        PickingGroup pickingGroup = MyApplication.getInstance().getPickingGroup();
        String pickType = userInfo.getPickingGroup().getPickType();
        List<CompletePickOrder.CompletePickingOrderItemList> list = new ArrayList<CompletePickOrder.CompletePickingOrderItemList>();
        if (pickingOrder != null) {
            for (PickingOrder.ItemsBean item : pickingOrder.getItems()) {
                CompletePickOrder.CompletePickingOrderItemList pickingOrderItemList = new CompletePickOrder.CompletePickingOrderItemList();
                if (pickType.equals("SUM_SHOP")) {
                    pickingOrderItemList.setPickItemID(item.getPickItemID());
                } else {
                    pickingOrderItemList.setBatPickItemID(item.getBatPickItemID());
                }
                pickingOrderItemList.setPickQty(item.getBuyQty());
                pickingOrderItemList.setSKU(pickType.equals("SUM_PRODUCT") ? pickingOrder.getSKU() : item.getSKU());
                pickingOrderItemList.setBuyQty(item.getBuyQty());
                list.add(pickingOrderItemList);
            }
        }
        CompletePickOrder completePickOrder = new CompletePickOrder();
        completePickOrder.setPickEmpID(userInfo.getEmpID());
        completePickOrder.setPickEmpName(userInfo.getEmpName());
        completePickOrder.setStationID(pickingGroup.getStationID());
        completePickOrder.setStationSort(pickingGroup.getStationSort());
        completePickOrder.setStationName(pickingGroup.getStationName());
        completePickOrder.setUserID(userInfo.getEmpID());
        completePickOrder.setUserName(userInfo.getEmpName());
        completePickOrder.setWID(userInfo.getWID());
        completePickOrder.setOpAreaID(userInfo.getOpAreaID());
        completePickOrder.setCompletePickingOrderItemList(list);
        if (pickType.equals("SUM_SHOP")) {
            completePickOrder.setPickID(pickingOrder.getPickID());
            getService().CompletePickOrder(completePickOrder).enqueue(new SimpleCallback<ApiResponse<String>>() {
                @Override
                public void onResponse(ApiResponse<String> result, int code, String msg) {
                    PickedSuccessful(result);
                }

                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    super.onFailure(call, t);
                    dismissProgressDialog();
                }
            });
        } else {
            completePickOrder.setBatPickID(pickingOrder.getBatPickID());
            getService().CompleteLinePickOrder(completePickOrder).enqueue(new SimpleCallback<ApiResponse<String>>() {
                @Override
                public void onResponse(ApiResponse<String> result, int code, String msg) {
                    PickedSuccessful(result);
                }

                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    super.onFailure(call, t);
                    dismissProgressDialog();
                }
            });
        }
    }

    private void PickedSuccessful(ApiResponse<String> result) {
        if (result.isSuccessful()) {
            List<PickingOrder> pickingOrders = MyApplication.getInstance().getPickingOrders();
            List<PickingOrder> historyOrders = MyApplication.getInstance().getmHistoryOrders();
            if (historyOrders.size() >= 10) {
                historyOrders.remove(historyOrders.size()-1);
            }
            historyOrders.add(0, pickingOrders.get(0));
            pickingOrders.remove(0);
            //已拣数量+1   总数量不变
            int finishedCnt = MyApplication.getInstance().getPickOrderSum().getFinishedCnt();
            MyApplication.getInstance().getPickOrderSum().setFinishedCnt(finishedCnt+1);
            if (pickingOrders.size() <= loadThreshold) {
                MyApplication instance = MyApplication.getInstance();
                getPickingOrder(instance.getStartDateStr(),
                        instance.getPickingGroup().getPGroupID(), instance.getPickingGroup().getStationID());
            }
            if (pickingOrders.size() > 0) {
                pickingOrder = pickingOrders.get(0);
                reloadRecyclerView();
            }
        } else {
            if (result.isAuthenticationFailed()) {
                ToastUtils.show(StartPickActivity.this, getString(R.string.authentication_failed));
                reLogin();
            } else {
                switch (result.getData()) {
                    case "Loss_PickingOrder_Error"://拣货单不存在
                    case "Data_Repeat_Error"://该工位已提交该拣货单拣货数据
                    case "PickingOrder_Status_Error":// 拣货单状态错误,不能完成拣货
                    {
                        UpdateMoneyDialog.getInstance(result.getInfo()).show(getSupportFragmentManager(), "fail");
                    }
                    break;
                    case "Status_Error"://保存拣货单-拣货APP-拣货状态表失败
                    case "Insert_PackingBox_Error"://创建配送单生成包装数失败
                    case "Create_DeliveryOrder_Error"://创建配送单发生错误
                    case "Param_Error"://传入参数错误
                    case "UnKnown_Error"://未知错误
                    case "Update_PickingStatus_Error"://更新拣货单表状态失败
                    case "Update_PickingItem_Error"://更新拣货单明细表拣货数量失败
                    {
                        ToastUtils.show(StartPickActivity.this, result.getInfo());
                    }
                    break;
                    default:
                        ToastUtils.show(StartPickActivity.this, result.getInfo());
                        break;
                }
            }
        }
        dismissProgressDialog();
    }
}
