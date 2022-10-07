package com.gemidroid.data.datasource.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gemidroid.data.datasource.localdb.entities.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherItem(item: WeatherEntity)

    @Query("select * from weather")
    fun getFavWeatherItems(): List<WeatherEntity>

    @Query("select * from weather where region=:locName")
    fun getFavWeatherItem(locName: String): WeatherEntity

    @Query("delete from weather where region = :name")
    fun removeWeatherItem(name: String? = "")
}