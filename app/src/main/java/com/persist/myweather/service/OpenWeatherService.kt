package com.persist.myweather.service

import com.persist.myweather.model.City
import com.persist.myweather.model.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather")
    fun getCityWeather(
        @Query("q") cityName: String,
        @Query("APPID") appId: String = "3ea94db66951adbe3fd407dfd2066c41"
    ): Call<City>

    @GET("find")
    fun findTemperature(
        @Query("q") cityName: String,
        @Query("units") units: String = "metrics",
        @Query("APPID") appId: String = "3ea94db66951adbe3fd407dfd2066c41"
    ): Call<Root>

}