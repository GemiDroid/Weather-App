package com.gemidroid.data.di

import android.content.Context
import com.gemidroid.data.datasource.localdb.db.WeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun buildWeatherDB(@ApplicationContext context: Context): WeatherDB = WeatherDB.buildDB(context)
}