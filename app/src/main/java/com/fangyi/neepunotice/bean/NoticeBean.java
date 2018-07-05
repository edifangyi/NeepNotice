package com.fangyi.neepunotice.bean;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/7/4
 * 说    明：
 * ================================================
 */
public class NoticeBean {
    private String title;
    private String time;
    private boolean read;

    public NoticeBean(String title, String time, boolean read) {
        this.title = title;
        this.time = time;
        this.read = read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
