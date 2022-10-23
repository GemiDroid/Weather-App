package com.gemidroid.data.datasource.localdb

import com.gemidroid.data.datasource.localdb.db.WeatherDB
import com.gemidroid.data.datasource.mapper.mapFromWeather
import com.gemidroid.data.datasource.mapper.mapFromWeatherEntity
import com.gemidroid.data.model.Weather
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDBDataSourceImpl
@Inject constructor(
    private val weatherDB: WeatherDB
) : ILocalDBDataSource {

    private val weatherDao = weatherDB.weatherDao

    override suspend fun getFavWeatherItems() = flow {
        emit(mapFromWeatherEntity(weatherDao.getFavWeatherItems()))
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