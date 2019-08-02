package com.frxs.wmspick.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.frxs.common_base.utils.DisplayUtil;
import com.frxs.common_base.utils.InputUtils;
import com.frxs.common_base.utils.SystemUtils;
import com.frxs.common_base.utils.ToastUtils;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.comms.Config;
import com.frxs.wmspick.model.PickingGroup;
import com.frxs.wmspick.model.UserInfo;
import com.frxs.wmspick.rest.model.AjaxParams;
import com.frxs.wmspick.rest.model.ApiResponse;
import com.frxs.wmspick.rest.model.ApiRequest;
import com.frxs.wmspick.rest.service.SimpleCallback;
import com.frxs.wmspick.widget.ChoosePathDialog;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2018/08/15
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class LoginActivity extends MyBaseActivity {
    EditText userNameEt;
    TextInputLayout userNameLayout;
    EditText passwordEt;
    TextInputLayout passwordLayout;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    private Button loginBtn;
    private String[] environments;
    private View selectEnvironment;
    int keyDownNum = 0;

    @Override
    protected void initView() {
        loginBtn = findViewById(R.id.login_btn);
        userNameLayout = findViewById(R.id.user_name_layout);
        passwordLayout = findViewById(R.id.password_layout);
        userNameEt = findViewById(R.id.user_name_et);
        passwordEt = findViewById(R.id.password_et);
        selectEnvironment = findViewById(R.id.select_environment);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) loginLayout.getLayoutParams();
        if (SystemUtils.isTabletDevice(this)) {
            layoutParams.width = (int)(DisplayUtil.getScreenWidth(this) * 0.8);
        }

        loginLayout.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        userNameLayout.setHint(getResources().getString(R.string.user_name));
        passwordLayout.setHint(getResources().getString(R.string.password));
        String account = MyApplication.getInstance().getUserAccount();
        if (!TextUtils.isEmpty(account)) {
            userNameEt.setText(account);
            userNameEt.setSelection(userNameEt.length());
        }

        initEnvironment();
    }

    private void initEnvironment() {
        environments = getResources().getStringArray(R.array.run_environments);
        for (int i = 0; i < environments.length; i++) {
            environments[i] = String.format(environments[i], Config.getBaseUrl(Config.TYPE_BASE, i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginVerify();
            }
        });
        selectEnvironment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyDownNum++;
                if (keyDownNum == 9) {
                    ToastUtils.showLongToast(LoginActivity.this, "再点击1次进入环境选择模式");
                }
                if (keyDownNum == 10) {
                    ToastUtils.showLongToast(LoginActivity.this, "已进入环境选择模式");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    final int spEnv = MyApplication.getInstance().getEnvironment();
                    String env = spEnv < environments.length ? environments[spEnv] : "";
                    dialog.setTitle(getResources().getString(R.string.tips_environment, env));
                    dialog.setCancelable(false);
                    dialog.setItems(environments, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, final int which) {
                            if (spEnv == which) {
                                return;
                            }
                            if (which != 0) {
                                final AlertDialog verifyMasterDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                View contentView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_evironments, null);
                                final EditText pswEt = contentView.findViewById(R.id.password_et);
                                contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (TextUtils.isEmpty(pswEt.getText().toString().trim())) {
                                            ToastUtils.show(LoginActivity.this, "密码不能为空！");
                                            return;
                                        }

                                        if (!pswEt.getText().toString().trim().equals(getString(R.string.str_psw))) {
                                            ToastUtils.show(LoginActivity.this, "密码错误！");
                                            return;
                                        }
                                        MyApplication.getInstance().setEnvironment(which);//存储所选择环境
                                        MyApplication.getInstance().resetRestClient();
                                        verifyMasterDialog.dismiss();
                                    }
                                });

                                contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        verifyMasterDialog.dismiss();
                                    }
                                });
                                verifyMasterDialog.setView(contentView);
                                verifyMasterDialog.show();

                            } else {
                                MyApplication.getInstance().setEnvironment(which);//存储所选择环境
                                MyApplication.getInstance().resetRestClient();
                            }

                        }
                    });
                    dialog.setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog.show();
                    keyDownNum = 0;
                }
            }
        });
    }

    private void loginVerify() {
        String username = userNameLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(username)) {
            userNameLayout.setError(getString(R.string.tips_null_account));
            userNameEt.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.tips_null_password));
            passwordEt.requestFocus();
        } else {
            if (InputUtils.isNumericOrLetter(password)) {
                userNameLayout.setErrorEnabled(false);
                passwordLayout.setErrorEnabled(false);
                requestLogin(username, password);
            } else {
                passwordLayout.setError(getString(R.string.tips_input_limit));
                passwordEt.requestFocus();
            }
        }
    }

    private void requestLogin(final String username, String password) {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        showProgressDialog();
        AjaxParams params = new AjaxParams();
        params.put("UserAccount", username);
        params.put("Password", password);
        params.put("UserType", 1);//value="1">拣货员; value="2">配送员; value="5">收货员; value="3">复核员;
        getService().UserLogin(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<UserInfo>>() {
            @Override
            public void onResponse(ApiResponse<UserInfo> result, int code, String msg) {
                dismissProgressDialog();
                if (result.isSuccessful()) {
                    MyApplication application = MyApplication.getInstance();
                    application.setUserAccount(username);

                    UserInfo userInfo = result.getData();
                    if (null != userInfo) {
                        application.setUserInfo(userInfo);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        gePickingGroup();
                    }
                } else {
                    ToastUtils.show(LoginActivity.this, result.getInfo());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserInfo>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });

    }

    private void gePickingGroup() {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        showProgressDialog();
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        ApiRequest params = new ApiRequest(this);
        params.put("EmpId", userInfo.getEmpID());
        params.put("WID", userInfo.getWID());
        params.put("OpAreaID", userInfo.getOpAreaID());
        getService().GetPickingGroup(params.getUrlParams()).enqueue(new SimpleCallback<ApiResponse<List<PickingGroup>>>() {
            @Override
            public void onResponse(ApiResponse<List<PickingGroup>> result, int code, String msg) {
                dismissProgressDialog();
                if (result.isSuccessful()) {
                    if (result.getData() != null) {
                        List<PickingGroup> PickingGroups = result.getData();
                        if (PickingGroups.size() > 1) {
                            ChoosePathDialog.getInstance((ArrayList<PickingGroup>) PickingGroups, null).show(getSupportFragmentManager(), "path_list");
                        } else if (1 == PickingGroups.size()) {
                            MyApplication.getInstance().setPickingGroup(PickingGroups.get(0));
                            gotoActivity(HomeActivity.class, true);
                        } else {
                            ToastUtils.show(LoginActivity.this, "该用户未绑定工位");
                        }
                    } else {
                        ToastUtils.show(LoginActivity.this, "该用户未绑定工位");
                    }
                } else {
                    if (result.isAuthenticationFailed()) {
                        ToastUtils.show(LoginActivity.this, getString(R.string.authentication_failed));
                        MyApplication.getInstance().logout();
                    } else {
                        ToastUtils.show(LoginActivity.this, result.getInfo());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PickingGroup>>> call, Throwable t) {
                super.onFailure(call, t);
                dismissProgressDialog();
            }
        });
    }
}
