package com.gemidroid.data.di

import com.gemidroid.data.datasource.sharedpref.ISharedPref
import com.gemidroid.data.datasource.sharedpref.SharedPrefImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPrefModule {

    @Binds
    abstract fun provideSharedPref(sharedPrefImpl: SharedPrefImpl): ISharedPref
}