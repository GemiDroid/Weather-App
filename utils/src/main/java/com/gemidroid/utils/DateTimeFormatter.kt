package com.gemidroid.utils

import android.util.Log
import org.joda.time.DateTime

fun formatToLocalDate(date: String?): String? {
    return try {
        DateTime.parse(date).toString("EEEE, dd MMM yyyy")
    } catch (e: Exception) {
        Log.e("TAG", "formatToLocalDate: ${e.printStackTrace()}")
        null
    }
}

fun formatToLocalTime(time: String): String? {
    return try {
        val time24 = time.split(":")[0]
        return time.plus(" ").plus(
            if (time24.toInt() > 12)
                "PM" else "AM"
        )
    } catch (e: Exception) {
        Log.e("TAG", "formatToLocalTime: ${e.localizedMessage} ")
        null
    }
}