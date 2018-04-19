package com.example.juicekaaa.fedtech10.Utils;

import com.example.juicekaaa.fedtech10.Assist.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Juicekaaa on 2017/6/29.
 */

public interface IWeatherApi {
    @GET("now?")
    Call<WeatherBean> getWeather(@Query("city") String city, @Query("key") String key);
}
