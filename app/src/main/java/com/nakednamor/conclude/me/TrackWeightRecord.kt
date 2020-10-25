package com.nakednamor.conclude.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.nakednamor.conclude.me.weight.*
import java.time.LocalDate
import java.time.LocalTime

class TrackWeightRecord : Fragment() {

    private lateinit var datePickerButton: Button
    private lateinit var timePickerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDatePickerResultListener()
        initializeTimePickerResultListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_weight_record, container, false)

        initializeDatePickerButton(view)
        initializeTimePickerButton(view)

        return view
    }

    private fun initializeDatePickerButton(view: View) {
        datePickerButton = view.findViewById(R.id.trackWeightInputDatePicker)
        val now = LocalDate.now()
        setDatePickerButtonText(now.year, now.monthValue, now.dayOfMonth)
        datePickerButton.setOnClickListener {
            val newFragment =
                DatePickerFragment.newInstance(now.year, now.monthValue, now.dayOfMonth)
            newFragment.show(parentFragmentManager, "weightDatePicker")
        }
    }

    private fun initializeTimePickerButton(view: View) {
        timePickerButton = view.findViewById(R.id.trackWeightInputTimePicker)
        val now = LocalTime.now()
        setTimePickerButtonText(now.hour, now.minute)
        timePickerButton.setOnClickListener {
            val newFragment = TimePickerFragment.newInstance(now.hour, now.minute)
            newFragment.show(parentFragmentManager, "weightTimePicker")
        }
    }

    private fun setDatePickerButtonText(year: Int, month: Int, day: Int) {
        datePickerButton.text = getString(R.string.date_picker_text, year, month, day)
    }

    private fun initializeDatePickerResultListener() =
        setFragmentResultListener(RESULT_KEY_DATE) { _, bundle ->
            val year = bundle.getInt(ARG_PARAM_YEAR)
            val month = bundle.getInt(ARG_PARAM_MONTH)
            val day = bundle.getInt(ARG_PARAM_DAY)

            setDatePickerButtonText(year, month, day)
        }


    private fun setTimePickerButtonText(hour: Int, minute: Int) {
        timePickerButton.text = getString(R.string.time_picker_text, hour, minute)
    }

    private fun initializeTimePickerResultListener() =
        setFragmentResultListener(RESULT_KEY_TIME) { _, bundle ->
            val hour = bundle.getInt(ARG_PARAM_HOUR)
            val minute = bundle.getInt(ARG_PARAM_MINUTE)

            setTimePickerButtonText(hour, minute)
        }
}