package com.example.juicekaaa.fedtech10.FragmentViewHolder;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class MeRightViewHolder {
    private int imgIcon;
    private String tvTitle, tvDate, tvAddress;

    public MeRightViewHolder(int imgIcon, String tvTitle, String tvDate, String tvAddress) {
        this.imgIcon = imgIcon;
        this.tvTitle = tvTitle;
        this.tvDate = tvDate;
        this.tvAddress = tvAddress;
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

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public String getTvAddress() {
        return tvAddress;
    }

    public void setTvAddress(String tvAddress) {
        this.tvAddress = tvAddress;
    }
}
