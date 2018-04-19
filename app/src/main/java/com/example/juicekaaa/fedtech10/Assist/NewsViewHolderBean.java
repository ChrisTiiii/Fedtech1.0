package com.example.juicekaaa.fedtech10.Assist;

import java.util.List;

/**
 * Created by Juicekaaa on 2017/7/10.
 */

public class NewsViewHolderBean {


    public List<NewsBean> news;
    public int isSport = -1;

    public int getIsSport() {
        return isSport;
    }

    public void setIsSport(int isSport) {
        this.isSport = isSport;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public static class NewsBean {
        /**
         * newsId : 新华日报
         * newsTitle : 百色公安项目第三周完成详情
         * newsWriter : 研发1
         * newsContent : 我们将项目的时间分为5个阶段（Phase），在不影响
         * newsTime : 3小时
         * imgHeader : /Users/macbook/Desktop/mok/index_img_head.png
         * imgPhoto : /Users/macbook/Desktop/mok/news_photo3.png
         * newsSport : 0
         */

        private String newsId;
        private String newsTitle;
        private String newsWriter;
        private String newsContent;
        private String newsTime;
        private String imgHeader;
        private String imgPhoto;
        private String newsSport;



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

        public String getImgHeader() {
            return imgHeader;
        }

        public void setImgHeader(String imgHeader) {
            this.imgHeader = imgHeader;
        }

        public String getImgPhoto() {
            return imgPhoto;
        }

        public void setImgPhoto(String imgPhoto) {
            this.imgPhoto = imgPhoto;
        }

        public String getNewsSport() {
            return newsSport;
        }

        public void setNewsSport(String newsSport) {
            this.newsSport = newsSport;
        }
    }
}
