package com.gemidroid.data.repo

import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IWeatherRepo {

    suspend fun getWeatherData(query: String): Flow<WeatherState>

    suspend fun searchForWeatherCity(query: String): Flow<WeatherState>

    suspend fun addItemToFav(item: Weather): Flow<WeatherState>

    suspend fun removeItemFromFav(itemName: String?): Flow<WeatherState>

    suspend fun getFavWeatherItems(): Flow<WeatherState>

    suspend fun getFavWeatherItem(name: String): Flow<WeatherState>
}