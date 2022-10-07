package com.gemidroid.data.datasource.network

import com.gemidroid.data.BuildConfig
import com.gemidroid.utils.ConnectionSettings
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor() {
    private val retrofit: Retrofit

    init {
        val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(ConnectionSettings.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ConnectionSettings.READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(ConnectionSettings.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun <T> getService(className: Class<T>): T {
        return retrofit.create(className)
    }
}