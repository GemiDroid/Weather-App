package com.gemidroid.weather.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemidroid.data.model.Weather
import com.gemidroid.data.model.WeatherState
import com.gemidroid.data.repo.IWeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val repo: IWeatherRepo) : ViewModel() {

    private val _getWeatherItems = MutableStateFlow<WeatherState>(WeatherState.Start)
    val getWeatherItems get() = _getWeatherItems

    private val _getWeatherLocations = MutableStateFlow<WeatherState>(WeatherState.Start)
    val getWeatherLocations get() = _getWeatherLocations

    val onSaveWeatherItem = MutableStateFlow<WeatherState>(WeatherState.Start)
    val onRemoveWeatherItem = MutableStateFlow<WeatherState>(WeatherState.Start)

    fun addWeatherToFav(weather: Weather) {
        weather.isFav = true
        viewModelScope.launch(Dispatchers.IO) {
            repo.addItemToFav(weather)
                .collect {
                    onSaveWeatherItem.emit(it)
                }
        }
    }

    fun removeWeatherFromFav(weather: Weather) {
        weather.isFav = false
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeItemFromFav(weather.location?.region)
                .collect {
                    onRemoveWeatherItem.emit(it)
                }
        }
    }

    fun getWeatherFavItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavWeatherItems().collect {
                _getWeatherItems.emit(it)
            }
        }
    }

    fun getWeatherFavItem(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavWeatherItem(name).collect {
                _getWeatherItems.emit(it)
            }
        }
    }

    fun getWeatherLocations(city: String) {
        viewModelScope.launch {
            repo.searchForWeatherCity(city)
                .collect {
                    _getWeatherLocations.emit(it)
                }
        }
    }

    fun getWeatherItems(query: String) {
        viewModelScope.launch {
            repo.getWeatherData(query)
                .onStart {
                    _getWeatherItems.emit(WeatherState.Loading(true))
                }
                .onCompletion {
                    _getWeatherItems.emit(WeatherState.Loading(false))
                }
                .collect {
                    _getWeatherItems.emit(it)
                }
        }
    }
}