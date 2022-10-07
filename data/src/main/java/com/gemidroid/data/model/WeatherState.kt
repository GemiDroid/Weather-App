package com.gemidroid.data.model

sealed class WeatherState {
    class Success<T>(val data: T? = null) : WeatherState()
    class Error(val error: Throwable) : WeatherState()
    class Loading(val isLoading: Boolean) : WeatherState()
    object Start : WeatherState()
}