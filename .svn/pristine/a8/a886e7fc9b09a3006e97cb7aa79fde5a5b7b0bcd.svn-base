package com.frxs.wmspick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.frxs.common_base.base.BaseActivity;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.adapter.ProductAdapter;
import com.frxs.wmspick.model.PickingOrder;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_LEFT;


/**
 * Created by Chentie on 2018/9/29.
 */

public class CheckOrderInfoActivity extends BaseActivity {

    private TextView titleTv;
    private TextView actionBackTv;
    private TextView tvStoreSore;
    private TextView tvProductSum;
    private TextView tvProductLines;
    private RecyclerView reView;
    private ProductAdapter reAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pick);
        //获取布局参数
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initView() {
        findViewById(R.id.toolbar).setBackgroundColor(getResources().getColor(R.color.frxs_gray));
        findViewById(R.id.finishPickTv).setVisibility(View.GONE);
        findViewById(R.id.actionRightTv).setVisibility(View.GONE);
        findViewById(R.id.finishPick).setBackgroundColor(getResources().getColor(R.color.frxs_gray_dull));
        titleTv = findViewById(R.id.titleTv);
        actionBackTv = findViewById(R.id.actionBackTv);
        tvStoreSore = findViewById(R.id.tv_store_sore);
        tvProductSum = findViewById(R.id.tv_product_sum);
        tvProductLines = findViewById(R.id.tv_product_lines);
        reView = findViewById(R.id.rcView);
        reView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        reAdapter = new ProductAdapter(R.layout.item_product, this, "history");
        reView.setAdapter(reAdapter);
        reAdapter.setEmptyView(R.layout.layout_empty, (ViewGroup) reView.getParent());
        reAdapter.openLoadAnimation(SLIDEIN_LEFT );
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            PickingOrder historyOrder = (PickingOrder)extras.getSerializable("history_order");
            updateOrderData(historyOrder);
            reAdapter.setNewData(historyOrder.getItems());
        }
    }

    private void initEvent() {
        actionBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置最新数据
     */
    private void updateOrderData(PickingOrder order) {
        String pickType = MyApplication.getInstance().getUserInfo().getPickingGroup().getPickType();
        switch (pickType) {
            case "SUM_SHOP": {//按门店拣货
                titleTv.setText(order.getLineName() + " - " + order.getStoreNo() + "-" + order.getStoreName());
                tvStoreSore.setText(order.getSendCardNo());
            }
            break;
            case "SUM_LINE": {//按线路拣货
                titleTv.setText(order.getLineName());
                tvStoreSore.setText("拣货完成");
            }
            break;
            case "SUM_PRODUCT": {//按商品拣货
                titleTv.setText(order.getProductName());
                tvStoreSore.setText("拣货完成");
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
        tvProductSum.setText("商品总数量：" + sum + "  " + String.format(getString(R.string.product_lines), String.valueOf(order.getItems().size())));
        tvProductLines.setVisibility(View.GONE);
    }
}
