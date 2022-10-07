package com.gemidroid.data.datasource.mapper

import com.gemidroid.data.datasource.localdb.entities.WeatherEntity
import com.gemidroid.data.model.*

fun mapFromWeatherEntity(weatherEntity: List<WeatherEntity>): List<Weather> {
    return weatherEntity.map {
        Weather(
            location = Location(
                name = it.name,
                region = it.region,
                country = it.country,
                localtime = it.localtime
            ),
            current = Current(
                tempC = it.tempC,
                tempF = it.tempF,
                it.wind,
                it.humidity,
                Condition(icon = it.icon, text = it.text)
            ),
            forecast = ForeCast(it.forecastDay),
            isFav = it.isAddedToFav
        )
    }
}

fun mapFromWeather(weather: List<Weather>): List<WeatherEntity> {
    return weather.map {
        WeatherEntity(
            isAddedToFav = it.isFav,
            name = it.location?.name,
            region = it.location?.region ?: "",
            country = it.location?.country,
            wind = it.current?.wind,
            humidity = it.current?.humidity,
            icon = it.current?.condition?.icon,
            text = it.current?.condition?.text,
            localtime = it.location?.localtime,
            tempC = it.current?.tempC,
            tempF = it.current?.tempF,
            forecastDay = it.forecast?.forecastDay
        )
    }
}