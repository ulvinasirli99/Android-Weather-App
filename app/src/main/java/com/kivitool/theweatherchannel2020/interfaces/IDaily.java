package com.kivitool.theweatherchannel2020.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDaily {

    @GET("forecast/daily")
    Call getMultipleDaysWeather(
            @Query("q") String q,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("cnt") int dayCount,
            @Query("appid") String appId
    );


}
