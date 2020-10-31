package com.nakednamor.conclude.me.data.weight

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "records_weight")
data class WeightRecord(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "weight") val weight: Float,
    @ColumnInfo(name = "recorded_at") val recordedAt: LocalDateTime
)