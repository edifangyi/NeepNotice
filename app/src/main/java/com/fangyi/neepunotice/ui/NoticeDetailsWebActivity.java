package com.fangyi.neepunotice.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.coolindicator.sdk.CoolIndicator;
import com.fangyi.component_library.base.BaseActivity;
import com.fangyi.component_library.widget.X5WebView;
import com.fangyi.neepunotice.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Calendar;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class NoticeDetailsWebActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTvTitle;

    private CoolIndicator mCoolIndicator;
    private X5WebView mWebView;

    private LinearLayout mLlRemind;
    private ImageView mIvRemind;
    private TextView mTvRemind;

    private String mString;
    private TimePickerDialog mTimePickerDialog;
    private DatePickerDialog mDatePickerDialog;

    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, NoticeDetailsWebActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_details_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();

        initToolbar();
        initRemind();
        initX5WebView();
        mLlRemind.setOnClickListener(v -> new MaterialDialog.Builder(mContext)
                .title("设置提醒")
                .items(R.array.remind)
                .itemsCallback((dialog, itemView, position, text) -> {

                    switch (position) {
                        case 0:
                            mIvRemind.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_access_alarm));
                            mTvRemind.setText("系统提醒：2018年07月04日 16:11");
                            break;
                        case 1:
                            mDatePickerDialog.show();
                            break;
                        case 2:
                            mTvRemind.setText("取消提醒");
                            mIvRemind.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_alarm_off));
                            break;
                    }
                })
                .show()
        );
    }

    private void initX5WebView() {
        mCoolIndicator.setMax(100);

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.clearCache(true);
        mWebView.clearHistory();

        mWebView.addJavascriptInterface(new TBSWebViewProxy(), "TBSWebViewProxy");

        mWebView.loadUrl("https://edifangyi.github.io/neepunotice_01.html");


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                mCoolIndicator.start();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mCoolIndicator.complete();
            }
        });

//        mWebView.loadUrl("file://" + Environment.getExternalStorageDirectory() + File.separator + "neepunotice_01.html");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    public class TBSWebViewProxy {

        @JavascriptInterface
        public void showAnnex(String url, String title) {
            NoticeDetailsTBSctivity.startAction((Activity) mContext, false, url, title);
        }
    }

    private void initToolbar() {
        mToolbar.setNavigationIcon(com.fangyi.component_library.R.drawable.ic_arrow_black);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("公告详情");
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }


    private void initRemind() {
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);
        mDatePickerDialog = new DatePickerDialog(mContext, (view, year, month, dayOfMonth) -> {
            mString = "自定义提醒：" + year + "日" + (month + 1) + "月" + dayOfMonth + "日";
            mTimePickerDialog.show();

        }, mYear, mMonth, mDay);

        mTimePickerDialog = new TimePickerDialog(mContext, (view, hourOfDay, minute) -> {

            mTvRemind.setText(mString + " " + (hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? "0" + minute : minute));

            mIvRemind.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_alarm_on));
        }, 0, 0, true);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mTvTitle = findViewById(R.id.tv_title);
        mLlRemind = findViewById(R.id.ll_remind);
        mIvRemind = findViewById(R.id.iv_remind);
        mTvRemind = findViewById(R.id.tv_remind);
        mCoolIndicator = findViewById(R.id.indicator);
        mWebView = findViewById(R.id.webView);
    }
}
