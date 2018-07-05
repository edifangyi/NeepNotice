package com.fangyi.neepunotice.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fangyi.component_library.base.BaseActivity;
import com.fangyi.neepunotice.R;
import com.fangyi.neepunotice.bean.NoticeBean;
import com.fangyi.neepunotice.func.adapter.NoticeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;

    private NoticeAdapter mAdapter;


    public static void startAction(Activity activity, boolean isFinish) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        initView();
        mToolbar.setTitle("通知公告");

        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//                if (oldState == RefreshState.LoadFinish && newState == RefreshState.None) {
//                    refreshLayout.autoRefresh();
//                    refreshLayout.setOnMultiPurposeListener(null);
//                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        List<NoticeBean> showList = new ArrayList<>();
        showList.add(new NoticeBean("关于推荐 2018 年度吉林省电机工程学会电力科学技术奖科技项目的通知", "2018年07月04日", false));
        showList.add(new NoticeBean("转发 2019 年吉林省产业创新专项资金项目（高技术产业部分）申报指南的通知", "2018年07月04日", false));
        showList.add(new NoticeBean("关于 2018 年度上半年博士科研启动基金项目结题验收情况的说明", "2018年07月02日", false));
        showList.add(new NoticeBean("文化和旅游部文化科技司关于推荐 2018 年度文化艺术研究项目和文化智库项目的通知  ", "2018年06月28日", false));
        showList.add(new NoticeBean("2018 年吉林市科技工作者创新创业大赛的通知", "2018年06月28日 ", false));
        showList.add(new NoticeBean("关于发布吉林省科技发展计划 2019 年度项目指南的通知", "2018年06月20日", false));
        showList.add(new NoticeBean("转发教育部办公厅 “关于推荐 2018 年度高等学校科学研究优秀成果奖（科学技术）的通知”", "2018年06月13日", false));
        showList.add(new NoticeBean("转发关于组织开展 2018 年上半年省教育厅科研项目验收工作的通知", "2018 年06月12日", false));
        showList.add(new NoticeBean("关于申请 2018 年度东北电力大学博士科研启动基金（第一批）项目的通知", "2018 年05月28日", false));
        showList.add(new NoticeBean("关于东北电力大学博士科研启动基金项目结题的通知", "2018年05月28日", false));

        mAdapter = new NoticeAdapter(showList);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NoticeDetailsActivity.startAction((Activity) mContext, false);
                showList.get(position).setRead(true);
                mAdapter.notifyItemChanged(position);
            }
        });
    }




    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);

    }

    private long triggerAtTimefirst = 0;

    @Override
    public void onBackPressed() {
        long triggerAtTimeSecond = triggerAtTimefirst;
        triggerAtTimefirst = SystemClock.elapsedRealtime();
        if (triggerAtTimefirst - triggerAtTimeSecond <= 2000) {
            finish();
        } else {
            Toast.makeText(mContext, "请再点击一次, 确认退出...", Toast.LENGTH_SHORT).show();
        }
    }
}
