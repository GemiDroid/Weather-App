package com.gemidroid.data.repo

import com.gemidroid.data.datasource.IDataSource
import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(val dataSource: IDataSource) : IWeatherRepo {

    override suspend fun getWeatherData(query: String) = flow {
        dataSource.getWeatherItems(query).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }

    override suspend fun searchForWeatherCity(query: String) = flow {
        dataSource.getWeatherLocations(query = query).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }

    }

    override suspend fun removeItemFromFav(itemName: String?) = flow {
        dataSource.removeWeatherFromFavourites(itemName)
            .catch {
                emit(WeatherState.Error(it))
            }
            .collect {
                emit(WeatherState.Success(it))
            }
    }

    override suspend fun addItemToFav(item: Weather) =
        flow {
            dataSource.saveWeatherToFavourites(item)
                .catch {
                    emit(WeatherState.Error(it))
                }
                .collect {
                    emit(WeatherState.Success(it))
                }
        }

    override suspend fun getFavWeatherItem(name: String) = flow {
        dataSource.getFavWeatherItem(name = name).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }

    override suspend fun getFavWeatherItems() = flow {
        dataSource.getFavWeatherItems().catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }
}