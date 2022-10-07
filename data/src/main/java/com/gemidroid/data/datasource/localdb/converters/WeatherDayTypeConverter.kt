package com.gemidroid.data.datasource.localdb.converters

import androidx.room.TypeConverter
import com.gemidroid.data.model.WeatherDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherDayTypeConverter {

    @TypeConverter
    fun fromEntity(value: List<WeatherDay>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherDay>?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toEntity(value: String): List<WeatherDay>? {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherDay>?>() {}.type
        return gson.fromJson(value, type)
    }
}