package com.nakednamor.conclude.me.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class Converters {

    @TypeConverter
    fun longToLocalDateTime(value: Long?): LocalDateTime? =
        value?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochSecond(value),
                TimeZone.getDefault().toZoneId()
            )
        }

    @TypeConverter
    fun localDateTimeToLong(value: LocalDateTime?): Long? =
        value?.let { value.atZone(TimeZone.getDefault().toZoneId()).toEpochSecond() }
}