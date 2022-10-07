package com.gemidroid.data.datasource.localdb.converters

import androidx.room.TypeConverter
import com.gemidroid.data.datasource.localdb.entities.WeatherEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {
    @TypeConverter
    fun fromEntity(value: WeatherEntity?): String {
        val gson = Gson()
        val type = object : TypeToken<WeatherEntity>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toEntity(value: String): WeatherEntity? {
        val gson = Gson()
        val type = object : TypeToken<WeatherEntity>() {}.type
        return gson.fromJson(value, type)
    }

}