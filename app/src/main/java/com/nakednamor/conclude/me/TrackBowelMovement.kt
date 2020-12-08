package com.nakednamor.conclude.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import com.nakednamor.conclude.me.fragement.*
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class TrackBowelMovement : Fragment(), View.OnClickListener, FragmentResultListener {

    private lateinit var datePickerField: TextView
    private lateinit var timePickerField: TextView

    private var now = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDatePickerResultListener()
        initializeTimePickerResultListener()
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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.trackBowelMovementInputDatePicker -> DatePickerFragment.newInstance(now.year, now.monthValue - 1, now.dayOfMonth).show(parentFragmentManager, "bowelMovementDatePicker")
            R.id.trackBowelMovementInputTimePicker -> TimePickerFragment.newInstance(now.hour, now.minute).show(parentFragmentManager, "bowelMovementTimePicker")
        }
    }

    override fun onFragmentResult(requestKey: String, bundle: Bundle) {
        when (requestKey) {
            RESULT_KEY_TIME -> {
                val hour = bundle.getInt(ARG_PARAM_HOUR)
                val minute = bundle.getInt(ARG_PARAM_MINUTE)

                setTimePickerButtonText(hour, minute)
            }
            RESULT_KEY_DATE -> {
                val year = bundle.getInt(ARG_PARAM_YEAR)
                val month = bundle.getInt(ARG_PARAM_MONTH) + 1
                val day = bundle.getInt(ARG_PARAM_DAY)

                setDatePickerButtonText(year, month, day)
            }
        }

        val chosenDatetime = getChosenDateTime(datePickerField, timePickerField)
        if (chosenDatetime.isAfter(now)) {
            datePickerField.error = getString(R.string.track_weight_datetime_error)
            timePickerField.error = getString(R.string.track_weight_datetime_error)
        } else {
            datePickerField.error = null
            timePickerField.error = null
        }
    }

    private fun initializeDatePickerResultListener() = setFragmentResultListener(RESULT_KEY_DATE, this::onFragmentResult)

    private fun initializeTimePickerResultListener() = setFragmentResultListener(RESULT_KEY_TIME, this::onFragmentResult)
}