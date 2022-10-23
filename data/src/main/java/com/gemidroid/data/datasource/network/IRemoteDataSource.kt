package com.gemidroid.data.datasource.network

import com.gemidroid.data.model.Location
import com.gemidroid.data.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface IRemoteDataSource {
    suspend fun getWeatherItems(query: String): Flow<Weather>
    suspend fun getWeatherLocations(query: String): Flow<List<Location>>
}