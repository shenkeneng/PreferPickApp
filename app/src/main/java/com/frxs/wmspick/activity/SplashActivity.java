package com.frxs.wmspick.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import com.frxs.wmspick.MyApplication;
import com.frxs.wmspick.R;
import com.frxs.wmspick.model.UserInfo;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/06/07
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class SplashActivity extends MyBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initData() {
        new CountDownTimer(3000, 1500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                UserInfo userInfo = ((MyApplication)MyApplication.getInstance()).getUserInfo();
                if (null != userInfo && userInfo.getPickingGroup() != null) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                SplashActivity.this.finish();
//                overridePendingTransition(R.anim.just_fade_in, R.anim.just_fade_out);
            }
        }.start();
    }

}
