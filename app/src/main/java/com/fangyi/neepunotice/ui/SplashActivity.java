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
import com.fangyi.component_library.utils.update.UpdateUtils;
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

        PermissionUtils.newBuilder()
                .requestPermission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE)
                .setOnGrantedListener(new PermissionUtils.OnGrantedListener() {
                    @Override
                    public void onSuccess() {
                        mHandler.postDelayed(() -> startAnim(), 1000);
                    }
                }).builder(mContext);

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

                UpdateUtils.getConfig()
                        .setUrl("http://www.mocky.io/v2/5b40b5322f0000810079e0e1")
                        .setOnUpdateListener(new UpdateUtils.OnUpdateListener() {
                            @Override
                            public void onNoUpdate() {
                                MainActivity.startAction((Activity) mContext, true);
                            }

                            @Override
                            public void onLater() {
                                MainActivity.startAction((Activity) mContext, true);
                            }

                            @Override
                            public void onIgnore(String newVersion) {
                                MainActivity.startAction((Activity) mContext, true);
                            }

                            @Override
                            public void onError(String message) {

                            }
                        })
                        .check(mContext);

            }
        });
    }

    private void initView() {
        mIvSplash = findViewById(R.id.iv_splash);
    }
}
