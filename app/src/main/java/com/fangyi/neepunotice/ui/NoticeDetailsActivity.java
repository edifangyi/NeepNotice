package com.fangyi.neepunotice.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fangyi.component_library.base.BaseActivity;
import com.fangyi.neepunotice.R;

import java.util.Calendar;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class NoticeDetailsActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvDate;
    private TextView mTvAnnex;
    private TextView mTvTime;


    private String mString;
    private TimePickerDialog mTimePickerDialog;

    private LinearLayout mLlRemind;
    private ImageView mIvRemind;
    private TextView mTvRemind;

    private DatePickerDialog mDatePickerDialog;



    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, NoticeDetailsActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_details;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();

        intContent();
        initRemind();
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

    private void intContent() {
        mTvTitle.setText("关于推荐 2018 年度吉林省电机工程学会电力科学技术奖科技项目的通知");
        mTvTime.setText("2018 年 07 月 04 日 11:02");
        mTvContent.setText("关于推荐 2018 年度吉林省电机工程学会电力科学技术奖科技项目的通知\n" +
                "\n" +
                " \n" +
                "\n" +
                "各院（系）及相关部门：\n" +
                "\n" +
                "2018 年省电机工程学会电力科技奖励项目现在开始申报，现将关事宜通知如下：\n" +
                "\n" +
                "一、推荐项目的条件\n" +
                "\n" +
                "1、所推荐的项目应是近两年完成的项目，项目应用时间应一年以上，有显著经济效益和社会效益，申报项目应以自主创新项目为主。\n" +
                "\n" +
                "2、推荐项目必须有鉴定验收和明确结论意见。\n" +
                "\n" +
                "3、推荐项目必须有科技项目查新报告。\n" +
                "\n" +
                "二、推荐项目的材料\n" +
                "\n" +
                "1、吉林省电机工程学会科学技术奖推荐书主件。\n" +
                "\n" +
                "2、吉林省电机工程学会科学技术奖推荐书主要附件。\n" +
                "\n" +
                "以上材料一律用 A4 纸打印，合并装订成册（模板见附件，共 2 册，正本、副本单独成册），另附所有材料电子档一份。\n" +
                "\n" +
                "三、推荐项目截止时间\n" +
                "\n" +
                "请于 2018 年 7 月 26 日前将推荐材料交到科技产业处成果与学术管理室（主楼 314 室），并推荐书电子文档发送到 247818895@qq.com, 统一上报省电机工程学会评审办。\n" +
                "\n" +
                "四、联系方式\n" +
                "\n" +
                "联系 人： 高凤凯\n" +
                "\n" +
                "联系电话： 15044251812");

        mTvAnnex.setText("附件：吉林省电力科学技术进步奖推荐书及填写说明");
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
        mTvContent = findViewById(R.id.tv_content);
        mTvDate = findViewById(R.id.tv_date);
        mTvAnnex = findViewById(R.id.tv_annex);
        mTvTime = findViewById(R.id.tv_time);

        mLlRemind = findViewById(R.id.ll_remind);
        mIvRemind = findViewById(R.id.iv_remind);
        mTvRemind = findViewById(R.id.tv_remind);

        mToolbar.setNavigationIcon(com.fangyi.component_library.R.drawable.ic_arrow_black);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("公告详情");
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

    }
}
