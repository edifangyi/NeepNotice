package com.fangyi.neepunotice.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fangyi.component_library.base.BaseActivity;
import com.fangyi.component_library.utils.permission.PermissionUtils;
import com.fangyi.neepunotice.R;
import com.yanzhenjie.permission.Permission;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class SplashActivity extends BaseActivity {

    private static final int ANIM_TIME = 2000;
    private static final float SCALE_END = 1.15F;

    private ImageView mIvSplash;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
//        PermissionUtils.newBuilder()
//                .requestPermission(
//                        Permission.ACCESS_FINE_LOCATION,
//                        Permission.ACCESS_COARSE_LOCATION,
//                        Permission.READ_EXTERNAL_STORAGE,
//                        Permission.WRITE_EXTERNAL_STORAGE,
//                        Permission.READ_PHONE_STATE,
//                        Permission.RECEIVE_MMS,
//                        Permission.RECEIVE_SMS,
//                        Permission.RECORD_AUDIO)
//                .setOnGrantedListener(() -> mHandler.postDelayed(() -> startAnim(), 1000));

        mHandler.postDelayed(() -> startAnim(), 1000);

    }

    private void startAnim() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                MainActivity.startAction((Activity) mContext, true);
            }
        });
    }

    private void initView() {
        mIvSplash = findViewById(R.id.iv_splash);
    }
}
