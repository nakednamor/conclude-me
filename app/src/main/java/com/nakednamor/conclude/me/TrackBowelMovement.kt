package com.nakednamor.conclude.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class TrackBowelMovement : Fragment(), View.OnClickListener {

    private lateinit var datePickerField: TextView
    private lateinit var timePickerField: TextView

    private var now = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_bowel_movement, container, false)

        initializeDatePicker(view)
        initializeTimePicker(view)

        return view
    }

    private fun initializeDatePicker(view: View) {
        datePickerField = view.findViewById(R.id.trackBowelMovementInputDatePicker)
        setDatePickerButtonText(now.year, now.monthValue, now.dayOfMonth)
        datePickerField.setOnClickListener(this)
    }

    private fun initializeTimePicker(view: View) {
        timePickerField = view.findViewById(R.id.trackBowelMovementInputTimePicker)
        setTimePickerButtonText(now.hour, now.minute)
        timePickerField.setOnClickListener(this)
    }

    private fun setDatePickerButtonText(year: Int, month: Int, day: Int) {
        datePickerField.text = getString(
            R.string.date_picker_text,
            year,
            prependZeroIfNeeded(month),
            prependZeroIfNeeded(day)
        )
    }

    private fun setTimePickerButtonText(hour: Int, minute: Int) {
        timePickerField.text = getString(
            R.string.time_picker_text,
            prependZeroIfNeeded(hour),
            prependZeroIfNeeded(minute)
        )
    }

    private fun prependZeroIfNeeded(value: Int): String = if (value <= 9) "0$value" else "$value"
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}