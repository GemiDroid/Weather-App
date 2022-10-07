package com.gemidroid.data.datasource

import com.gemidroid.data.BuildConfig
import com.gemidroid.data.datasource.localdb.db.WeatherDB
import com.gemidroid.data.datasource.mapper.mapFromWeather
import com.gemidroid.data.datasource.mapper.mapFromWeatherEntity
import com.gemidroid.data.datasource.network.ApiService
import com.gemidroid.data.datasource.network.NetworkClient
import com.gemidroid.data.model.Weather
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    networkClient: NetworkClient,
    weatherDB: WeatherDB
) : IDataSource {

    private val weatherApi = networkClient.getService(ApiService::class.java)

    private val weatherDao = weatherDB.weatherDao

    override suspend fun getWeatherItems(query: String) = flow {
        emit(weatherApi.getWeatherData(BuildConfig.WEATHER_API_KEY, query = query))
    }

    override suspend fun getFavWeatherItems() = flow {
        emit(mapFromWeatherEntity(weatherDao.getFavWeatherItems()))
    }

    override suspend fun getWeatherLocations(query: String) = flow {
        emit(weatherApi.searchForWeatherCity(BuildConfig.WEATHER_API_KEY, query))
    }

    override suspend fun saveWeatherToFavourites(weatherItem: Weather) = flow {
        emit(weatherDao.insertWeatherItem(mapFromWeather(listOf(weatherItem))[0]))
    }

    override suspend fun getFavWeatherItem(name: String) = flow {
        emit(mapFromWeatherEntity(listOf(weatherDao.getFavWeatherItem(name)))[0])
    }

    override suspend fun removeWeatherFromFavourites(item: String?) = flow {
        emit(weatherDao.removeWeatherItem(item))
    }
}