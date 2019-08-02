package com.frxs.wmspick.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frxs.common_base.utils.DateUtil;
import com.frxs.common_base.utils.SystemUtils;
import com.frxs.common_base.utils.ToastUtils;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.model.PickingGroup;
import com.frxs.wmspick.model.PickingOrderCount;
import com.frxs.wmspick.rest.model.ApiRequest;
import com.frxs.wmspick.rest.model.ApiResponse;
import com.frxs.wmspick.rest.service.SimpleCallback;
import com.frxs.wmspick.widget.UpdateMoneyDialog;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;


/**
 * Created by Chentie on 2018/9/12.
 */

public class HomeActivity extends MyBaseActivity {

    private TextView titleTv;
    private TextView actionBackTv;
    private LinearLayout llStartPick;
    private TextView tvDate;
    private PickingGroup pickingGroup;
    private MyApplication instance;
    private TextView tvUserName;
    private TextView tvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        actionBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMoneyDialog.getInstance("是否退出当前账号？").show(getSupportFragmentManager(), "logout");
            }
        });
        llStartPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickOrderCount();
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(Calendar.getInstance());
            }
        });
    }

    /**
     * 获取拣货单数量
     */
    private void getPickOrderCount() {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        showProgressDialog();
        ApiRequest params = new ApiRequest(this);
        params.put("DeliveryTime", instance.getStartDateStr());
        params.put("PGroupID", instance.getPickingGroup().getPGroupID());
        params.put("StationID", instance.getPickingGroup().getStationID());
        params.put("PickType", instance.getUserInfo().getPickingGroup().getPickType());
        params.put("UserID", getUserID());
        params.put("UserName", getUserName());
        params.put("WID", getWID());
        params.put("OpAreaID", getOpAreaID());
        getService().GetPickingOrderStatisticCnt(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<PickingOrderCount>>() {
            @Override
            public void onResponse(ApiResponse<PickingOrderCount> result, int code, String msg) {
                if (result.isSuccessful()) {
                    if (result.getData() != null) {
                        PickingOrderCount data = result.getData();
                        instance.setPickOrderSum(data);
                        if (data.getUnFinishedCnt() <= 0) {
                            UpdateMoneyDialog.getInstance("暂无拣货单").show(getSupportFragmentManager(), "no_pick");
                        } else {
                            gotoActivity(StartPickActivity.class, false);
                        }
                    }
                } else {
                    if (result.isAuthenticationFailed()) {
                        ToastUtils.show(HomeActivity.this, getString(R.string.authentication_failed));
                        reLogin();
                    } else {
                        ToastUtils.show(HomeActivity.this, result.getInfo());
                    }
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ApiResponse<PickingOrderCount>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void initView() {
        titleTv = findViewById(R.id.titleTv);
        actionBackTv = findViewById(R.id.actionBackTv);
        actionBackTv.setText("退出登录");
        llStartPick = findViewById(R.id.ll_start_pick);
        tvDate = findViewById(R.id.tv_date);
        tvUserName = findViewById(R.id.tv_user_name);
        tvVersion = findViewById(R.id.tv_version);
        findViewById(R.id.actionRightTv).setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        instance = MyApplication.getInstance();
        pickingGroup = instance.getPickingGroup();
        tvUserName.setText("拣货员：" + instance.getUserInfo().getEmpName());
        if (pickingGroup != null) {
            titleTv.setText(pickingGroup.getPGroupName() + "-" + pickingGroup.getStationName());
            /**
             * 设置初始化日期
             */
            setDeliveryDate();
        }
        tvVersion.setText( "兴盛优选拣货客户端 ：" + SystemUtils.getAppVersion(this));
    }

    public void showDatePickerDialog(Calendar calendar) {
      new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              String monthStr = (month + 1) < 10 ? "0" + (month + 1) : String.valueOf(month + 1);
              String dayStr = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
              String date = year + "-" + monthStr + "-" + dayStr;
              tvDate.setText("提货日期：" + date);
              MyApplication.getInstance().setStartDateStr(date);
              MyApplication.getInstance().getPickingOrders().clear();
          }

      }, calendar.get(Calendar.YEAR)
              , calendar.get(Calendar.MONTH)
              , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDeliveryDate();
    }

    private void setDeliveryDate() {

        String startDateStr = MyApplication.getInstance().getStartDateStr();
        if (TextUtils.isEmpty(startDateStr)) {
            Date pickDate = DateUtil.addDateHours(new Date(), 14); //默认当天十点之后的订单提货日期是第二天
            startDateStr = DateUtil.Date2StringYMD(pickDate);
            instance.setStartDateStr(startDateStr);
        }

        tvDate.setText("提货日期：" + startDateStr);
    }
}
