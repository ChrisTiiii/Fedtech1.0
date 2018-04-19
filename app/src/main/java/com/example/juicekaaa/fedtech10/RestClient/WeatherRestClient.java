package com.example.juicekaaa.fedtech10.RestClient;

import com.example.juicekaaa.fedtech10.Utils.IWeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juicekaaa on 2017/7/3.
 */

public class WeatherRestClient {
    private Retrofit mRetrofit;
    private IWeatherApi mIWeatherApi;
    private final static String BASE_URL = "https://free-api.heweather.com/v5/";


    public WeatherRestClient() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mIWeatherApi = mRetrofit.create(IWeatherApi.class);

    }

    public IWeatherApi getmIWeatherApi() {
        if (mIWeatherApi != null)
            return mIWeatherApi;

        return null;
    }
}
