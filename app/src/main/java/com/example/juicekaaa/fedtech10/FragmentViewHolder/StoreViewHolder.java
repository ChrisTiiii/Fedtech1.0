package com.example.juicekaaa.fedtech10.FragmentViewHolder;

/**
 * Created by Juicekaaa on 17/6/15.
 */

public class StoreViewHolder {
    private String tvTitle, tvDetail;
    private int imgPhoth;

    public StoreViewHolder(String tvTitle, String tvDetail, int imgPhoth) {
        this.tvTitle = tvTitle;
        this.tvDetail = tvDetail;
        this.imgPhoth = imgPhoth;
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

    public int getImgPhoth() {
        return imgPhoth;
    }

    public void setImgPhoth(int imgPhoth) {
        this.imgPhoth = imgPhoth;
    }

}
