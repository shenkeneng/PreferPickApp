package com.frxs.wmspick.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.activity.CheckOrderInfoActivity;
import com.frxs.wmspick.activity.HomeActivity;
import com.frxs.wmspick.activity.LoginActivity;
import com.frxs.wmspick.activity.StartPickActivity;
import com.frxs.wmspick.model.PickingGroup;
import com.frxs.wmspick.model.PickingOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chentie on 2018/9/20.
 */

public class ChoosePathDialog extends DialogFragment {

    private Activity activity;
    private Context context;
    private LayoutInflater layoutInflater;
    private Dialog dialog;
    private View view;
    private RecyclerView chooseRcview;
    private ArrayList<PickingGroup> pathList;
    private ArrayList<PickingOrder> orderList;
    private ChoosePathAdapter pathAdapter;
    private ChooseHistoryOrderAdapter historyAdapter;
    private String tag;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
        this.context = context;
    }

    public static ChoosePathDialog getInstance(ArrayList<PickingGroup> listPath, ArrayList<PickingOrder> PickingGroups) {
        ChoosePathDialog choosePathDialog = new ChoosePathDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("path_list", listPath);
        bundle.putSerializable("order_list", PickingGroups);
        choosePathDialog.setArguments(bundle);
        return choosePathDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getTag() != null && !getTag().isEmpty()){
            tag = getTag();
        }
        layoutInflater = LayoutInflater.from(context);
        dialog = new Dialog(context, R.style.bran_online_supervise_dialog);
        view = layoutInflater.inflate(R.layout.dialog_choose, null);
        chooseRcview = view.findViewById(R.id.choose_rcview);
        chooseRcview.setLayoutManager(new LinearLayoutManager(context));
        chooseRcview.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));

        //设置取消标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置点击外部不可以消失
        dialog.setCanceledOnTouchOutside(true);
        //设置即使点击返回键也不会退出
        setCancelable(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        Bundle bundle = getArguments();

        switch (tag) {
            case "path_list": {
                if (null != bundle) {
                    pathList = (ArrayList<PickingGroup>) bundle.getSerializable("path_list");
                }
                pathAdapter = new ChoosePathAdapter(R.layout.item_path, pathList);
                chooseRcview.setAdapter(pathAdapter);
                pathAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        PickingGroup item = (PickingGroup) adapter.getItem(position);
                        MyApplication.getInstance().setPickingGroup(item);

                        ((LoginActivity) activity).gotoActivity(HomeActivity.class, true);
                        dialog.dismiss();
                    }
                });
            }
            break;
            case "order_list":{
                if (null != bundle) {
                    orderList = (ArrayList<PickingOrder>) bundle.getSerializable("order_list");
                }
                historyAdapter = new ChooseHistoryOrderAdapter(R.layout.item_path, orderList);
                chooseRcview.setAdapter(historyAdapter);
                historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        PickingOrder item = (PickingOrder) adapter.getItem(position);
                        Bundle historyOrder = new Bundle();
                        historyOrder.putSerializable("history_order", item);
                        ((StartPickActivity) activity).gotoActivity(CheckOrderInfoActivity.class, false, historyOrder);
                        dialog.dismiss();
                    }
                });
            }

                break;
            default:
                break;
        }
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置软键盘弹出模式
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        //设置Dialog宽度匹配屏幕宽度
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置Dialog高度自适应
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        WindowManager winManager=(WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
        int width = winManager.getDefaultDisplay().getWidth();
        int height = winManager.getDefaultDisplay().getHeight();
        dialog.getWindow().setLayout(width, height / 2);//setLayout必须 在 setContentView之后, 调用;并且 setBackgroundDrawable 必须设置  这里的-1,-2可以设置为任意高度;
        return dialog;
    }

    class ChoosePathAdapter extends BaseQuickAdapter<PickingGroup, BaseViewHolder>{

        public ChoosePathAdapter(int layoutResId, @Nullable List<PickingGroup> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PickingGroup item) {
            helper.setText(R.id.tv_num, String.valueOf(helper.getPosition()+1));
            helper.setText(R.id.tv_path, item.getPGroupName() + " - " + item.getStationName());
        }
    }

    class ChooseHistoryOrderAdapter extends BaseQuickAdapter<PickingOrder, BaseViewHolder>{

        public ChooseHistoryOrderAdapter(int layoutResId, @Nullable List<PickingOrder> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PickingOrder item) {
            String pickType = MyApplication.getInstance().getUserInfo().getPickingGroup().getPickType();
            helper.getView(R.id.tv_num).setVisibility(View.GONE);
            switch (pickType) {
                case "SUM_SHOP": {//按门店拣货
                    helper.setText(R.id.tv_path, item.getSendCardNo());
                }
                break;
                case "SUM_LINE": {//按线路拣货
                    helper.setText(R.id.tv_path, String.valueOf(item.getLineName()));
                }
                break;
                case "SUM_PRODUCT": {//按商品拣货
                    helper.setText(R.id.tv_path, String.valueOf(item.getProductName()));
                }
                break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
