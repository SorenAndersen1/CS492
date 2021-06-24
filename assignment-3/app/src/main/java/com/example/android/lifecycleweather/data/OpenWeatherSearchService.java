package com.example.android.lifecycleweather.data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenWeatherSearchService {
    @GET("forecast")
    Call<FiveDayForecast> searchRepos(@Query("q") String city, @Query("units") String units, @Query("appid") String appid);

}
