package com.frxs.wmspick.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frxs.common_base.utils.MathUtils;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.model.PickingOrder;

/**
 * Created by Chentie on 2018/9/19.
 */

public class ProductAdapter extends BaseQuickAdapter<PickingOrder.ItemsBean, BaseViewHolder> {

    private Context context;
    private String history;

    public ProductAdapter(int layoutResId, Context context, String history) {
        super(layoutResId);
        this.context = context;
        this.history = history;
    }

    @Override
    protected void convert(BaseViewHolder helper, final PickingOrder.ItemsBean item) {
        helper.setText(R.id.tvProductNum, MathUtils.doubleTrans(item.getBuyQty()));
        String pickType = MyApplication.getInstance().getPickingGroup().getPickType();
        if (history.equals("history") || item.isPick() == false) {// 历史拣货单 或者拣货单未拣货时
            helper.setText(R.id.tvProductName, pickType.equals("SUM_PRODUCT") ? item.getLineName() :
                    Html.fromHtml(String.format(context.getString(R.string.product_name), String.valueOf(item.getShelfCode()), item.getProductName(), item.getSKUContent()))).setTextColor(R.id.tvProductName, context.getResources().getColor(R.color.frxs_gray))
                    .setBackgroundRes(R.id.tvProductNum, R.drawable.shape_bg_item_ye)
                    .setTextColor(R.id.tvProductNum, context.getResources().getColor(R.color.frxs_red))
                    .setChecked(R.id.cb, false);
            helper.getView(R.id.cb).setVisibility(history.equals("history") ? View.GONE : View.VISIBLE);
            helper.addOnClickListener(history.equals("history") ? 0 : R.id.llBg);
        } else { //正在拣货的拣货单
            helper.setText(R.id.tvProductName, pickType.equals("SUM_PRODUCT") ? item.getLineName() :
                    item.getShelfCode() + " " + item.getProductName() + "\n" + item.getSKUContent())//使用string资源将无法正常改变文字颜色
                    .setTextColor(R.id.tvProductName, context.getResources().getColor(R.color.frxs_gray_dark))
                    .setBackgroundRes(R.id.tvProductNum, 0)
                    .setTextColor(R.id.tvProductNum, context.getResources().getColor(R.color.frxs_gray_dark))
                    .setChecked(R.id.cb, true);
            helper.addOnClickListener(R.id.llBg);
        }
        TextView tvFoot = helper.getView(R.id.tv_foot);
        if (helper.getAdapterPosition() == getItemCount() - 1) {
            tvFoot.setVisibility(View.VISIBLE);
        } else {
            tvFoot.setVisibility(View.GONE);
        }
    }
}
