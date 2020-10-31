package com.nakednamor.conclude.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.nakednamor.conclude.me.data.AppDatabase
import com.nakednamor.conclude.me.data.weight.WeightDao
import com.nakednamor.conclude.me.data.weight.WeightRecord
import com.nakednamor.conclude.me.weight.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime

class TrackWeightRecord : Fragment(), View.OnClickListener, FragmentResultListener {

    private lateinit var datePickerField: TextView
    private lateinit var timePickerField: TextView
    private lateinit var weightInput: EditText
    private lateinit var addWeightButton: Button

    private lateinit var weightDao: WeightDao

    private var now = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDatePickerResultListener()
        initializeTimePickerResultListener()

        weightDao = AppDatabase.getInstance(requireContext()).weightDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_track_weight_record, container, false)

        initializeDatePicker(view)
        initializeTimePicker(view)
        initializeWeightInput(view)
        initializeAddWeightButton(view)

        return view
    }

    private fun initializeDatePicker(view: View) {
        datePickerField = view.findViewById(R.id.trackWeightInputDatePicker)
        setDatePickerButtonText(now.year, now.monthValue, now.dayOfMonth)
        datePickerField.setOnClickListener(this)
    }

    private fun initializeTimePicker(view: View) {
        timePickerField = view.findViewById(R.id.trackWeightInputTimePicker)
        setTimePickerButtonText(now.hour, now.minute)
        timePickerField.setOnClickListener(this)
    }

    private fun initializeWeightInput(view: View) {
        weightInput = view.findViewById(R.id.trackWeightInputNumber)
    }

    private fun initializeAddWeightButton(view: View) {
        addWeightButton = view.findViewById(R.id.trackWeightInputButton)
        addWeightButton.setOnClickListener(this)
    }

    private fun setDatePickerButtonText(year: Int, month: Int, day: Int) {
        datePickerField.text = getString(
            R.string.date_picker_text,
            year,
            prependZeroIfNeeded(month),
            prependZeroIfNeeded(day)
        )
    }

    private fun initializeDatePickerResultListener() = setFragmentResultListener(RESULT_KEY_DATE, this::onFragmentResult)

    private fun setTimePickerButtonText(hour: Int, minute: Int) {
        timePickerField.text = getString(
            R.string.time_picker_text,
            prependZeroIfNeeded(hour),
            prependZeroIfNeeded(minute)
        )
    }

    private fun initializeTimePickerResultListener() = setFragmentResultListener(RESULT_KEY_TIME, this::onFragmentResult)

    private fun prependZeroIfNeeded(value: Int): String = if (value <= 9) "0$value" else "$value"

    override fun onClick(view: View) {
        when (view.id) {
            R.id.trackWeightInputDatePicker -> DatePickerFragment.newInstance(now.year, now.monthValue, now.dayOfMonth).show(parentFragmentManager, "weightDatePicker")
            R.id.trackWeightInputTimePicker -> TimePickerFragment.newInstance(now.hour, now.minute).show(parentFragmentManager, "weightTimePicker")
            R.id.trackWeightInputButton -> this.insertWeightRecord()
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
                val month = bundle.getInt(ARG_PARAM_MONTH)
                val day = bundle.getInt(ARG_PARAM_DAY)

                setDatePickerButtonText(year, month, day)
            }
        }
    }

    private fun insertWeightRecord() {
        val weight = weightInput.text.toString()    // TODO check if input valid
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                weightDao.insertWeightRecord(WeightRecord(weight = weight.toFloat()))
                Toast.makeText(context, "weight saved", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(context, "error while saving weight: " + ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}