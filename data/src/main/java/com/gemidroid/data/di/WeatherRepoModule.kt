package com.gemidroid.data.di;

import com.gemidroid.data.repo.IWeatherRepo
import com.gemidroid.data.repo.WeatherRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherRepoModule() {

    @Binds
    abstract fun providesWeatherRepo(weatherRepo: WeatherRepoImpl): IWeatherRepo
}
