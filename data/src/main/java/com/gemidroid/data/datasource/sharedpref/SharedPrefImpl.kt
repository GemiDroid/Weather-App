package com.gemidroid.data.datasource.sharedpref

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(
    @ApplicationContext context: Context
) : ISharedPref {

    private var sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            app_preferences, Context.MODE_PRIVATE
        )


    override var isTempCeliusis: Boolean
        get() = sharedPreferences.getBoolean(IS_CELIUIS_TEMP, false)
        set(value) {
            sharedPreferences.edit().putBoolean(IS_CELIUIS_TEMP, value).apply()
        }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val IS_CELIUIS_TEMP = "c temp"
        private const val app_preferences = "SEHHATY_PREFERENCES"
    }
}