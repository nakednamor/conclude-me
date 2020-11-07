package com.nakednamor.conclude.me.data.weight

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightRecord(record: WeightRecord): Long

    @Query("SELECT * FROM records_weight ORDER BY recorded_at DESC")
    fun getWeightRecordsByRecordingTimeDesc(): LiveData<List<WeightRecord>>
}