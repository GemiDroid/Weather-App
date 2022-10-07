package com.gemidroid.data.di

import com.gemidroid.data.datasource.DataSourceImpl
import com.gemidroid.data.datasource.IDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherDSModule {

    @Binds
    abstract fun providesWeatherDS(dataSourceImpl: DataSourceImpl): IDataSource
}