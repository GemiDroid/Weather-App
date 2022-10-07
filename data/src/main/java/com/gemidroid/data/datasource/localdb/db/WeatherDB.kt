package com.gemidroid.data.datasource.localdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gemidroid.data.BuildConfig
import com.gemidroid.data.datasource.localdb.converters.WeatherDayTypeConverter
import com.gemidroid.data.datasource.localdb.converters.WeatherTypeConverter
import com.gemidroid.data.datasource.localdb.dao.WeatherDao
import com.gemidroid.data.datasource.localdb.entities.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
@TypeConverters(WeatherTypeConverter::class, WeatherDayTypeConverter::class)

abstract class WeatherDB : RoomDatabase() {

    abstract val weatherDao: WeatherDao

    companion object {
        private var weatherInstanceDB: WeatherDB? = null

        @Synchronized
        fun buildDB(context: Context): WeatherDB {
            if (weatherInstanceDB == null)
                weatherInstanceDB = Room.databaseBuilder(
                    context, WeatherDB::class.java,
                    BuildConfig.WEATHER_DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return weatherInstanceDB!!
        }
    }
}