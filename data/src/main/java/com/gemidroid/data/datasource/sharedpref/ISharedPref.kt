package com.gemidroid.data.datasource.sharedpref

import javax.inject.Singleton

@Singleton
interface ISharedPref {
    var isTempCeliusis: Boolean
    fun clear()
}
