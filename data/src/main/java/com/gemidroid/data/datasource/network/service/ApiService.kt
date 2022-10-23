package com.gemidroid.data.datasource.network.service

import com.gemidroid.data.BuildConfig
import com.gemidroid.data.model.Location
import com.gemidroid.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(BuildConfig.GET_FORECAST_WEATHER)
    suspend fun getWeatherData(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int = 5,
        @Query("aqi") airQuality: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Weather

    @GET(BuildConfig.SEARCH_WEATHER_CITY)
    suspend fun searchForWeatherCity(
        @Query("key") apiKey: String,
        @Query("q") query: String,
    ): List<Location>
}