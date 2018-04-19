package com.example.juicekaaa.fedtech10.RestClient;


import com.example.juicekaaa.fedtech10.Utils.INewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juicekaaa on 2017/7/10.
 */

public class NewsClient {
    private Retrofit retrofit;
    private INewsApi iNewsApi;
    private final static String BASE_URL = "http://192.168.100.44:5050/";


    public NewsClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        iNewsApi = retrofit.create(INewsApi.class);
    }

    public INewsApi getiNewsApi() {
        if (iNewsApi != null)
            return iNewsApi;
        return null;

    }
}
