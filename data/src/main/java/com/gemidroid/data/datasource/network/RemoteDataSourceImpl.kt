package com.gemidroid.data.datasource.network

import com.gemidroid.data.BuildConfig
import com.gemidroid.data.datasource.network.client.NetworkClient
import com.gemidroid.data.datasource.network.service.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    networkClient: NetworkClient,
) : IRemoteDataSource {

    private val weatherApi = networkClient.getService(ApiService::class.java)

    override suspend fun getWeatherItems(query: String) = flow {
        emit(weatherApi.getWeatherData(BuildConfig.WEATHER_API_KEY, query = query))
    }

    override suspend fun getWeatherLocations(query: String) = flow {
        emit(weatherApi.searchForWeatherCity(BuildConfig.WEATHER_API_KEY, query))
    }
}