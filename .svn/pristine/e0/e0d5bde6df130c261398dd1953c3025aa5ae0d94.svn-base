package com.frxs.wmspick.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frxs.common_base.base.BaseActivity;
import com.frxs.common_base.widget.CountEditText;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.activity.HomeActivity;
import com.frxs.wmspick.activity.LoginActivity;

/**
 * Created by Chentie on 2018/9/18.
 */

public class UpdateMoneyDialog extends DialogFragment {

    private Activity activity;
    private Context context;
    private LayoutInflater inflater;
    private Dialog dialog;
    private View view;
    private TextView tvCancel;
    private TextView tvConfirm;
    CountEditText countEditText;
    private String tag;
    private String info;
    private LinearLayout llInfo;
    private TextView tvInfo;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    public static UpdateMoneyDialog getInstance(String name){
        UpdateMoneyDialog imgShowDialog = new UpdateMoneyDialog();
        Bundle bundle = new Bundle();
        bundle.putString("str",name);
        //传入值，跟Fragment传值方法一样
        imgShowDialog.setArguments(bundle);
        return imgShowDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getTag() != null && !getTag().isEmpty()){
            tag = getTag();
        }
        inflater = LayoutInflater.from(context);
        dialog = new Dialog(context, R.style.bran_online_supervise_dialog);
        view = inflater.inflate(R.layout.dialog_product, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvConfirm = view.findViewById(R.id.tv_confirm);

        Bundle bundle = getArguments();
        if(null != bundle){
            info = bundle.getString("str");
        }

        switch (tag) {
            case "logout":
                tvTitle.setText(info);
                break;
            case "product":
                view.findViewById(R.id.ll_product).setVisibility(View.VISIBLE);
                countEditText = view.findViewById(R.id.countEdit);
                countEditText.setCount(Integer.valueOf(info));
                break;
            case "info":
                tvTitle.setText(info);
                tvCancel.setVisibility(View.GONE);
                break;
            case "fail":
                tvTitle.setText("拣货失败");
                llInfo = view.findViewById(R.id.ll_info);
                tvInfo = view.findViewById(R.id.tv_info);
                llInfo.setVisibility(View.VISIBLE);
                tvInfo.setText(info + "请返回首页。");
                break;
            case "no_pick":
                tvTitle.setText("提示");
                llInfo = view.findViewById(R.id.ll_info);
                tvInfo = view.findViewById(R.id.tv_info);
                llInfo.setVisibility(View.VISIBLE);
                tvInfo.setText(info);
                tvCancel.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        //设置取消标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置点击外部不可以消失
        dialog.setCanceledOnTouchOutside(false);
        //设置即使点击返回键也不会退出
        setCancelable(false);
        dialog.setContentView(view);

        tvCancel.setOnClickListener(onClickListener);
        tvConfirm.setOnClickListener(onClickListener);

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
        return dialog;
    }

    /**
     * View的点击事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_cancel:
                    dialog.dismiss();
                    break;
                case R.id.tv_confirm:
                    switch (tag) {
                        case "logout":
                            MyApplication.getInstance().logout();
                            ((BaseActivity)context).gotoActivity(LoginActivity.class, true);
                            break;
                        case "info":
                        case "fail":
                            MyApplication.getInstance().getPickingOrders().clear();
                            ((BaseActivity)context).gotoActivity(HomeActivity.class, true);
                            break;
                        case "no_pick":
                            dialog.dismiss();
                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    break;
            }
        }
    };

    public interface OnUpdateMoneyListener{
        void onObtainValue(int position,String data);
    }
    private OnUpdateMoneyListener listener = null;
    public void setOnUpdateMoneyListener(OnUpdateMoneyListener listener){
        this.listener = listener;
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
