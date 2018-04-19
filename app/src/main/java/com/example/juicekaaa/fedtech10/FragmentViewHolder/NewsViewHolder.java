package com.example.juicekaaa.fedtech10.FragmentViewHolder;

import java.util.List;

/**
 * Created by Juicekaaa on 17/6/14.
 */

public class NewsViewHolder {

    public List<NewsViewHolder> mList;


    public String newsId, newsTitle, newsWriter, newsContent, newsTime;
    public int imgHeader, imgPhoto;
    public int newsSport;
    public int isSport = -1;

    public int getIsSport() {
        return isSport;
    }

    public void setIsSport(int isSport) {
        this.isSport = isSport;
    }

    public NewsViewHolder(String newsId, String newsTitle, String newsWriter, String newsContent, String newsTime, int imgHeader, int imgPhoto, int newsSport) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsWriter = newsWriter;
        this.newsContent = newsContent;
        this.newsTime = newsTime;
        this.imgHeader = imgHeader;
        this.imgPhoto = imgPhoto;
        this.newsSport = newsSport;
    }


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsWriter() {
        return newsWriter;
    }

    public void setNewsWriter(String newsWriter) {
        this.newsWriter = newsWriter;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public int getImgHeader() {
        return imgHeader;
    }

    public void setImgHeader(int imgHeader) {
        this.imgHeader = imgHeader;
    }

    public int getImgPhoto() {
        return imgPhoto;
    }

    public void setImgPhoto(int imgPhoto) {
        this.imgPhoto = imgPhoto;
    }

    public int getNewsSport() {
        return newsSport;
    }

    public void setNewsSport(int newsSport) {
        this.newsSport = newsSport;
    }

    public List<NewsViewHolder> getmList() {
        return mList;
    }

    public void setmList(List<NewsViewHolder> mList) {
        this.mList = mList;
    }

}