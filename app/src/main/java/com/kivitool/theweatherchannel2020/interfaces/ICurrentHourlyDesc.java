package com.kivitool.theweatherchannel2020.interfaces;

import com.kivitool.theweatherchannel2020.ui.current_day_hourly.HCallback;
import com.kivitool.theweatherchannel2020.ui.forecast_hourly.HourlyForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICurrentHourlyDesc {

    @GET("onecall?")
    Call<HCallback> getHourlyData(

            @Query("lat") double lat,

            @Query("lon") double lon,

            @Query("%20exclude") String  exclude,

            @Query("appid") String appid

    );



}
