package com.gemidroid.data.repo

import com.gemidroid.data.datasource.network.IRemoteDataSource
import com.gemidroid.data.datasource.localdb.ILocalDBDataSource
import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDBDataSource
) : IWeatherRepo {

    override suspend fun getWeatherData(query: String) = flow {
        remoteDataSource.getWeatherItems(query).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }

    override suspend fun searchForWeatherCity(query: String) = flow {
        remoteDataSource.getWeatherLocations(query = query).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }

    }

    override suspend fun removeItemFromFav(itemName: String?) = flow {
        localDataSource.removeWeatherFromFavourites(itemName)
            .catch {
                emit(WeatherState.Error(it))
            }
            .collect {
                emit(WeatherState.Success(it))
            }
    }

    override suspend fun addItemToFav(item: Weather) =
        flow {
            localDataSource.saveWeatherToFavourites(item)
                .catch {
                    emit(WeatherState.Error(it))
                }
                .collect {
                    emit(WeatherState.Success(it))
                }
        }

    override suspend fun getFavWeatherItem(name: String) = flow {
        localDataSource.getFavWeatherItem(name = name).catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }

    override suspend fun getFavWeatherItems() = flow {
        localDataSource.getFavWeatherItems().catch {
            emit(WeatherState.Error(it.fillInStackTrace()))
        }.collect {
            emit(WeatherState.Success(it))
        }
    }
}