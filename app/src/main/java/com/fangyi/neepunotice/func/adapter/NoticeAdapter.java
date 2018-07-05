package com.fangyi.neepunotice.func.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fangyi.neepunotice.bean.NoticeBean;
import com.fangyi.neepunotice.R;

import java.util.List;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class NoticeAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {

    public NoticeAdapter(@Nullable List<NoticeBean> data) {
        super(R.layout.item_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getTime());


        helper.setText(R.id.tv_read, item.isRead() ? "已读" : "未读");
        helper.setTextColor(R.id.tv_read, ContextCompat.getColor(mContext, item.isRead() ? R.color.green : R.color.red));
    }


}