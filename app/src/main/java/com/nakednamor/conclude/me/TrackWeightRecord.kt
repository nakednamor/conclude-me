package com.nakednamor.conclude.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.nakednamor.conclude.me.weight.*
import java.time.LocalDate
import java.time.LocalTime

class TrackWeightRecord : Fragment() {

    private lateinit var datePickerField: TextView
    private lateinit var timePickerField: TextView

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

        initializeDatePicker(view)
        initializeTimePicker(view)

        return view
    }

    private fun initializeDatePicker(view: View) {
        datePickerField = view.findViewById(R.id.trackWeightInputDatePicker)
        val now = LocalDate.now()
        setDatePickerButtonText(now.year, now.monthValue, now.dayOfMonth)
        datePickerField.setOnClickListener {
            val newFragment =
                DatePickerFragment.newInstance(now.year, now.monthValue, now.dayOfMonth)
            newFragment.show(parentFragmentManager, "weightDatePicker")
        }
    }

    private fun initializeTimePicker(view: View) {
        timePickerField = view.findViewById(R.id.trackWeightInputTimePicker)
        val now = LocalTime.now()
        setTimePickerButtonText(now.hour, now.minute)
        timePickerField.setOnClickListener {
            val newFragment = TimePickerFragment.newInstance(now.hour, now.minute)
            newFragment.show(parentFragmentManager, "weightTimePicker")
        }
    }

    private fun setDatePickerButtonText(year: Int, month: Int, day: Int) {
        datePickerField.text = getString(R.string.date_picker_text, year, month, day)
    }

    private fun initializeDatePickerResultListener() =
        setFragmentResultListener(RESULT_KEY_DATE) { _, bundle ->
            val year = bundle.getInt(ARG_PARAM_YEAR)
            val month = bundle.getInt(ARG_PARAM_MONTH)
            val day = bundle.getInt(ARG_PARAM_DAY)

            setDatePickerButtonText(year, month, day)
        }


    private fun setTimePickerButtonText(hour: Int, minute: Int) {
        timePickerField.text = getString(R.string.time_picker_text, hour, minute)
    }

    private fun initializeTimePickerResultListener() =
        setFragmentResultListener(RESULT_KEY_TIME) { _, bundle ->
            val hour = bundle.getInt(ARG_PARAM_HOUR)
            val minute = bundle.getInt(ARG_PARAM_MINUTE)

            setTimePickerButtonText(hour, minute)
        }
}