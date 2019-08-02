package com.frxs.wmspick.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.frxs.common_base.base.BaseActivity;
import com.frxs.common_base.utils.EasyPermissionsEx;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.comms.Config;
import com.frxs.wmspick.model.UserInfo;
import com.frxs.wmspick.rest.service.ApiService;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2018/08/15
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public abstract class MyBaseActivity extends BaseActivity{
    protected ApiService mService;
    private static final int MY_PERMISSIONS_REQUEST_WES = 2;// 请求文件存储权限的标识码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof SplashActivity)) {
            // 判断当前用户是否允许存储权限
            if (EasyPermissionsEx.hasPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE})) {
                // 允许 - 执行更新方法
                if (MyApplication.getInstance().isNeedCheckUpgrade()) {
                    MyApplication.getInstance().prepare4Update(this, false);
                }
            } else {
                // 不允许 - 弹窗提示用户是否允许放开权限
                EasyPermissionsEx.executePermissionsRequest(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_WES);
            }
        }
    }

    public ApiService getService() {
        return MyApplication.getRestClient(Config.TYPE_BASE).getApiService();
    }

    public void reLogin() {
        MyApplication.getInstance().logout();
        gotoActivity(LoginActivity.class, true);
    }

    public String getToken() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        return userInfo != null ? userInfo.getToken() : "";
    }

    public String getUserAccount() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        return userInfo != null ? userInfo.getUserAccount() : "";
    }

    public int getUserID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            return userInfo.getEmpID();
        } else {
            return 0;
        }
    }

    public String getUserName() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            return userInfo.getEmpName();
        } else {
            return "";
        }
    }

    public String getWID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();

        if (null != userInfo) {
            return String.valueOf(userInfo.getWID());
        } else {
            return "";
        }
    }

    public int getOpAreaID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();

        if (null != userInfo) {
            return userInfo.getOpAreaID();
        } else {
            return 0;
        }
    }

    /**
     * 请求用户是否放开权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WES: {// 版本更新存储权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 已获取权限 继续运行应用
                    if (MyApplication.getInstance().isNeedCheckUpgrade()) {
                        MyApplication.getInstance().prepare4Update(this, false);
                    }
                } else {
                    // 不允许放开权限后，提示用户可在去设置中跳转应用设置页面放开权限。
                    if (!EasyPermissionsEx.somePermissionPermanentlyDenied(this, permissions)) {
                        EasyPermissionsEx.goSettings2PermissionsDialog(this, "需要文件存储权限来下载更新的内容,但是该权限被禁止,你可以到设置中更改");
                    }
                }
                break;
            }
        }
    }
}
