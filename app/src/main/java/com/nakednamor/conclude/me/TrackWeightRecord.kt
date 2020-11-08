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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nakednamor.conclude.me.adapter.WeightRecordListAdapter
import com.nakednamor.conclude.me.data.weight.WeightRecord
import com.nakednamor.conclude.me.data.weight.WeightRepository
import com.nakednamor.conclude.me.util.NumericTextWatcher
import com.nakednamor.conclude.me.viewmodels.LastWeightRecordsViewModel
import com.nakednamor.conclude.me.weight.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class TrackWeightRecord : Fragment(), View.OnClickListener, FragmentResultListener {

    private lateinit var datePickerField: TextView
    private lateinit var timePickerField: TextView
    private lateinit var weightInput: EditText
    private lateinit var addWeightButton: Button

    @Inject
    lateinit var weightRepository: WeightRepository

    private val viewModel: LastWeightRecordsViewModel by viewModels()

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
        val view = inflater.inflate(R.layout.fragment_track_weight_record, container, false)

        initializeDatePicker(view)
        initializeTimePicker(view)
        initializeWeightInput(view)
        initializeAddWeightButton(view)
        initializeLastRecordsView(view)

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
        weightInput.addTextChangedListener(NumericTextWatcher(weightInput, 1, getString(R.string.track_weight_input_error), this::setAddWeightButtonVisibility))
    }

    private fun initializeAddWeightButton(view: View) {
        addWeightButton = view.findViewById(R.id.trackWeightInputButton)
        addWeightButton.setOnClickListener(this)
        addWeightButton.isEnabled = allInputsValidValue()
    }

    private fun initializeLastRecordsView(view: View) {
        val weightView: RecyclerView = view.findViewById(R.id.lastWeightRecordsView)
        val context = requireContext()
        val adapter = WeightRecordListAdapter(context)
        weightView.adapter = adapter
        weightView.layoutManager = LinearLayoutManager(context)

        viewModel.weightRecords.observe(viewLifecycleOwner) { weightRecords -> adapter.setWeightRecords(weightRecords) }
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
            R.id.trackWeightInputDatePicker -> DatePickerFragment.newInstance(now.year, now.monthValue - 1, now.dayOfMonth).show(parentFragmentManager, "weightDatePicker")
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
                val month = bundle.getInt(ARG_PARAM_MONTH) + 1
                val day = bundle.getInt(ARG_PARAM_DAY)

                setDatePickerButtonText(year, month, day)
            }
        }
    }

    private fun insertWeightRecord() {
        val weight = weightInput.text.toString()    // TODO check if input valid
        val dateString = datePickerField.text
        val timeString = timePickerField.text

        val dateTime = LocalDateTime.of(
            dateString.split("-")[0].toInt(),
            dateString.split("-")[1].toInt(),
            dateString.split("-")[2].toInt(),
            timeString.split(":")[0].toInt(),
            timeString.split(":")[1].toInt()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                weightRepository.insert(WeightRecord(weight = weight.toFloat(), recordedAt = dateTime))
                Toast.makeText(context, "weight saved", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(context, "error while saving weight: " + ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAddWeightButtonVisibility() {
        addWeightButton.isEnabled = allInputsValidValue()
    }

    private fun allInputsValidValue() = weightInput.error == null && weightInput.text.isNotEmpty()   // TODO validate if date and time is <= current time
}