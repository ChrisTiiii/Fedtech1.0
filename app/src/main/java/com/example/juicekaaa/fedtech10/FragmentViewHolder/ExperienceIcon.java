package com.example.juicekaaa.fedtech10.FragmentViewHolder;

/**
 * Created by Juicekaaa on 17/6/22.
 */

public class ExperienceIcon {
    private int icon;
    private String tvTitle, tvDetail;

    public ExperienceIcon(int icon, String tvTitle, String tvDetail) {
        this.icon = icon;
        this.tvTitle = tvTitle;
        this.tvDetail = tvDetail;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvDetail() {
        return tvDetail;
    }

    public void setTvDetail(String tvDetail) {
        this.tvDetail = tvDetail;
    }
}
