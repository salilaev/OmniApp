package com.salilaev.omni_app.data.local.prefs

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeSharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    fun setTime(time: Long){
        prefs.edit().putLong("time", time).apply()
    }

    fun getTime():Long{
        return prefs.getLong("time",0)
    }
}