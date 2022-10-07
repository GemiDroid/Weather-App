package com.gemidroid.data.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("forecast")
    val forecast: ForeCast? = null,
    var isFav: Boolean? = false
)

data class ForeCast(
    @SerializedName("forecastday")
    val forecastDay: List<WeatherDay>? = emptyList()
)

data class WeatherDay(val day: Day? = null)

data class Day(
    @SerializedName("maxtemp_f")
    val maxTempF: Double? = 0.0,
    @SerializedName("mintemp_f")
    val minTempF: Double? = 0.0,
    val condition: Condition
)

data class Current(
    @SerializedName("temp_c")
    val tempC: Double? = 0.0,
    @SerializedName("temp_f")
    val tempF: Double? = 0.0,
    @SerializedName("wind_mph")
    val wind: Double? = 0.0,
    @SerializedName("humidity")
    val humidity: Double? = 0.0,
    val condition: Condition
)

data class Condition(val text: String? = "", val icon: String? = "")

data class Location(
    val region: String = "",
    val name: String? = "",
    val country: String? = "",
    @SerializedName("localtime_epoch") val dateTimeStamp: Long? = 0L,
    val localtime: String? = ""
)