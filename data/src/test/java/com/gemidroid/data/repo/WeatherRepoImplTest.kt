package com.gemidroid.data.repo

import com.gemidroid.data.model.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherRepoImplTest {

   private var weather = Weather()

    @Test
    fun check_weather_data_is_empty() {
        val weatherItem = Weather(
            Location("Test", "Test", "Test"),
            current = Current(10.0, 140.0, 14.0, 12.9, Condition("https://www.google.com")),
            forecast = ForeCast(
                listOf(
                    WeatherDay(
                        Day(
                            120.0,
                            20.0,
                            Condition("https://www.google.com")
                        )
                    )
                )
            )
        )
        assert(weatherItem == weather)
    }

    @Test
    fun check_weather_country_is_null_empty() {
        val weatherItem = Weather(
            Location("Test", "", "Country"),
            current = Current(10.0, 140.0, 14.0, 12.9, Condition("https://www.google.com")),
            forecast = ForeCast(
                listOf(
                    WeatherDay(
                        Day(
                            120.0,
                            20.0,
                            Condition("https://www.google.com")
                        )
                    )
                )
            )
        )
        assert(weatherItem.location?.country.isNullOrEmpty())
    }
}