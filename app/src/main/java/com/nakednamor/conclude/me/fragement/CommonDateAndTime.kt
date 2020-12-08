package com.nakednamor.conclude.me.fragement

import android.widget.TextView
import java.time.LocalDateTime

fun prependZeroIfNeeded(value: Int): String = if (value <= 9) "0$value" else "$value"

fun getChosenDateTime(datePickerField: TextView, timePickerField: TextView): LocalDateTime {
    val dateString = datePickerField.text
    val timeString = timePickerField.text

    return LocalDateTime.of(
        dateString.split("-")[0].toInt(),
        dateString.split("-")[1].toInt(),
        dateString.split("-")[2].toInt(),
        timeString.split(":")[0].toInt(),
        timeString.split(":")[1].toInt()
    )
}

fun isChosenDateTimeValid(datePickerField: TextView, timePickerField: TextView, now: LocalDateTime = LocalDateTime.now()) = getChosenDateTime(datePickerField, timePickerField).isBefore(now)
