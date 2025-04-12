package com.salilaev.data.repository

import com.salilaev.data.local.prefs.TimeSharedPref
import com.salilaev.domain.repository.TimeRepository
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor(
    private val pref: TimeSharedPref
):TimeRepository {
    override suspend fun getTime(): Long {
        return pref.getTime()
    }

    override suspend fun setTime(time: Long) {
        pref.setTime(time)
    }
}