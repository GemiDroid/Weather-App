package com.gemidroid.data.datasource.localdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gemidroid.data.model.WeatherDay

@Entity(tableName = "weather")
data class WeatherEntity(
    val name: String? = "",
    @PrimaryKey
    val region: String = "",
    val country: String? = "",
    val wind: Double? = 0.0,
    val humidity: Double? = 0.0,
    val text: String? = "",
    val icon: String? = "",
    val localtime: String? = "",
    val tempC: Double? = 0.0,
    val tempF: Double? = 0.0,
    val forecastDay: List<WeatherDay>? = null,
    var isAddedToFav: Boolean? = false
)
