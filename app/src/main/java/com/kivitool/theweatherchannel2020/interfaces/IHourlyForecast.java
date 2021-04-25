package com.kivitool.theweatherchannel2020.interfaces;

import com.kivitool.theweatherchannel2020.ui.forecast_hourly.HourlyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IHourlyForecast {


    @GET("forecast?")
    Call<HourlyForecast> getHourlyData(

            @Query("lat") double lat,

            @Query("lon") double lon,

            @Query("units") String units,

            @Query("lang") String lang,

            @Query("appid") String appid

    );


}
