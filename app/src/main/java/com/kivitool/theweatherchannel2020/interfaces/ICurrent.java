package com.kivitool.theweatherchannel2020.interfaces;

import com.kivitool.theweatherchannel2020.ui.currentweather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICurrent {


    @GET("weather?")
    Call<CurrentWeather> getCurrentWeatherData(

            @Query("lat") double lat,

            @Query("lon") double lon,

            @Query("units") String units,

            @Query("lang") String lang,

            @Query("appid") String appId

    );




}
