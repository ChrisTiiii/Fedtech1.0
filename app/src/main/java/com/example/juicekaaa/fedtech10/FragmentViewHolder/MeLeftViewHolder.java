package com.example.juicekaaa.fedtech10.FragmentViewHolder;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class MeLeftViewHolder {
    private int imgIcon;
    private String tvTitle, tvExplain, tvUseDate,tvTime;

    public MeLeftViewHolder(int imgIcon, String tvTitle, String tvExplain, String tvUseDate, String tvTime) {
        this.imgIcon = imgIcon;
        this.tvTitle = tvTitle;
        this.tvExplain = tvExplain;
        this.tvUseDate = tvUseDate;
        this.tvTime = tvTime;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvExplain() {
        return tvExplain;
    }

    public void setTvExplain(String tvExplain) {
        this.tvExplain = tvExplain;
    }

    public String getTvUseDate() {
        return tvUseDate;
    }

    public void setTvUseDate(String tvUseDate) {
        this.tvUseDate = tvUseDate;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }
}
