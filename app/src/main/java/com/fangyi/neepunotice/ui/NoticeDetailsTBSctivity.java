package com.fangyi.neepunotice.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.coolindicator.sdk.CoolIndicator;
import com.fangyi.component_library.base.BaseActivity;
import com.fangyi.component_library.widget.TBSWebView;
import com.fangyi.component_library.widget.X5WebView;
import com.fangyi.neepunotice.R;
import com.socks.library.KLog;
import com.tencent.smtt.sdk.WebSettings;
import com.yanzhenjie.kalle.Headers;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.download.Callback;
import com.yanzhenjie.kalle.download.Download;

import java.io.File;
import java.util.Calendar;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class NoticeDetailsTBSctivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTvTitle;

    private TBSWebView mTbsWebView;
    private CoolIndicator mCoolIndicator;

    public static void startAction(Activity activity, boolean isFinish, String url, String title) {
        Intent intent = new Intent(activity, NoticeDetailsTBSctivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_details_tbs;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initToolbar();
        initTBSWebView();

    }


    private void initTBSWebView() {


        mCoolIndicator.setMax(100);

        mTbsWebView.setOnGetFilePathListener(new TBSWebView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(TBSWebView tbsWebView) {
                downloadFile(getIntent().getStringExtra("url"));
            }
        });

        mTbsWebView.show();


    }

    private void downloadFile(String url) {
        Kalle.Download.get(url)
                .directory(Environment.getExternalStorageDirectory() + File.separator + "NeepuNotice" + File.separator + "AnnexCache")
                .policy(new Download.Policy() {
                    @Override
                    public boolean isRange() {
                        return true;
                    }

                    @Override
                    public boolean allowDownload(int code, Headers headers) {
                        return true;
                    }

                    @Override
                    public boolean oldAvailable(String path, int code, Headers headers) {
                        return true;
                    }
                })
                .onProgress((progress, byteCount, speed) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mCoolIndicator.setProgress(progress,true);
                    }
                })
                .perform(new Callback() {
                    @Override
                    public void onStart() {
                        mCoolIndicator.start();
                    }

                    @Override
                    public void onFinish(String path) {
                        mCoolIndicator.complete();
                        mTbsWebView.displayFile(path);
                    }

                    @Override
                    public void onException(Exception e) {
                        KLog.e("======");
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onEnd() {
                        mCoolIndicator.complete();
                    }
                });
    }


    private void initToolbar() {
        String title = getIntent().getStringExtra("title");

        mToolbar.setNavigationIcon(com.fangyi.component_library.R.drawable.ic_arrow_black);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsWebView.onStopDisplay();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mTvTitle = findViewById(R.id.tv_title);

        mTbsWebView = findViewById(R.id.tbsWebView);
        mCoolIndicator = findViewById(R.id.indicator);
    }
}
