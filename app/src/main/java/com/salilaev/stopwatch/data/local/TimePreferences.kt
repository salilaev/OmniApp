package com.salilaev.stopwatch.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "timer_prefs")

@Singleton
class TimePreferences @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val TIMER_KEY = longPreferencesKey("timer_value")
    }

    suspend fun saveTimerValue(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[TIMER_KEY] = time
        }
    }

    suspend fun getTimerFlow(): Long = context.dataStore.data.first()[TIMER_KEY] ?: 0L
}