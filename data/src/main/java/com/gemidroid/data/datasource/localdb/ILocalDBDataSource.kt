package com.gemidroid.data.datasource.localdb

import com.gemidroid.data.model.Weather
import kotlinx.coroutines.flow.Flow

interface ILocalDBDataSource {
    suspend fun getFavWeatherItems(): Flow<List<Weather>>

    suspend fun getFavWeatherItem(name: String): Flow<Weather>

    suspend fun saveWeatherToFavourites(weatherItem: Weather): Flow<Unit>

    suspend fun removeWeatherFromFavourites(item: String?): Flow<Unit>

}