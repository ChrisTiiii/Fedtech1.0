package com.example.juicekaaa.fedtech10.Utils;

import com.example.juicekaaa.fedtech10.Assist.NewsViewHolderBean;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.NewsViewHolder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Juicekaaa on 2017/7/10.
 */

public interface INewsApi {
    @GET("getFedtechNews/{pageCount}/{pageIndex}")
    Call<NewsViewHolder> getNews(@Path("pageCount") int pageCount, @Path("pageIndex") int pageIndex);
}
