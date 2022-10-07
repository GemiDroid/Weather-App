package com.gemidroid.data.datasource

import com.gemidroid.data.model.Location
import com.gemidroid.data.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IDataSource {

    suspend fun getWeatherItems(query: String): Flow<Weather>

    suspend fun getFavWeatherItems(): Flow<List<Weather>>

    suspend fun getFavWeatherItem(name: String): Flow<Weather>

    suspend fun getWeatherLocations(query: String): Flow<List<Location>>

    suspend fun saveWeatherToFavourites(weatherItem: Weather): Flow<Unit>

    suspend fun removeWeatherFromFavourites(item: String?): Flow<Unit>
}