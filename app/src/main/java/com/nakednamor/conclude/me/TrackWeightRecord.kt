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

        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            val year = bundle.getInt(ARG_PARAM_YEAR)
            val month = bundle.getInt(ARG_PARAM_MONTH)
            val day = bundle.getInt(ARG_PARAM_DAY)

            datePickerButton.text = "$year-$month-$day"
        }

        setFragmentResultListener(RESULT_KEY_TIME) { _, bundle ->
            val hour = bundle.getInt(ARG_PARAM_HOUR)
            val minute = bundle.getInt(ARG_PARAM_MINUTE)

            timePickerButton.text = "$hour:$minute"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_weight_record, container, false)

        datePickerButton = view.findViewById(R.id.trackWeightInputDatePicker)
        val now = LocalDate.now()
        datePickerButton.setOnClickListener {
            val newFragment =
                DatePickerFragment.newInstance(now.year, now.monthValue, now.dayOfMonth)
            newFragment.show(parentFragmentManager, "weightDatePicker")
        }

        timePickerButton = view.findViewById(R.id.trackWeightInputTimePicker)
        val xxx = LocalTime.now()
        timePickerButton.setOnClickListener {
            val newFragment = TimePickerFragment.newInstance(xxx.hour, xxx.minute)
            newFragment.show(parentFragmentManager, "weightTimePicker")
        }

        return view
    }


}