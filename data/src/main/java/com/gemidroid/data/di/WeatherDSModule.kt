package com.gemidroid.data.di

import com.gemidroid.data.datasource.localdb.ILocalDBDataSource
import com.gemidroid.data.datasource.localdb.LocalDBDataSourceImpl
import com.gemidroid.data.datasource.network.IRemoteDataSource
import com.gemidroid.data.datasource.network.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherDSModule {

    @Binds
    abstract fun providesWeatherLocalDS(locaDataSourceImpl: LocalDBDataSourceImpl): ILocalDBDataSource

    @Binds
    abstract fun providesWeatherRemoteDS(remoteDataSourceImpl: RemoteDataSourceImpl): IRemoteDataSource
}