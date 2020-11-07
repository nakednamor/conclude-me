package com.nakednamor.conclude.me.data.weight

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeightRepository @Inject constructor(
    private val weightDao: WeightDao
) {

    fun getLastWeightRecords(): LiveData<List<WeightRecord>> = weightDao.getWeightRecordsByRecordingTimeDesc()

    suspend fun insert(record: WeightRecord): Long = weightDao.insertWeightRecord(record)
}