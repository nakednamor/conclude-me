package com.nakednamor.conclude.me.weight

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.time.LocalTime

const val ARG_PARAM_HOUR = "hour"
const val ARG_PARAM_MINUTE = "minute"
const val RESULT_KEY_TIME = "pickedTime"

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var hour: Int? = null
    private var minute: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            hour = it.getInt(ARG_PARAM_HOUR)
            minute = it.getInt(ARG_PARAM_MINUTE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val act = activity ?: throw RuntimeException("no activity found")
        val now = LocalTime.now()
        return TimePickerDialog(
            act,
            this,
            hour ?: now.hour,
            minute ?: now.minute,
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, pickedHour: Int, pickedMinute: Int) {
        setFragmentResult(
            RESULT_KEY_TIME,
            bundleOf(
                ARG_PARAM_HOUR to pickedHour,
                ARG_PARAM_MINUTE to pickedMinute
            )
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(hour: Int, minute: Int): TimePickerFragment {
            val instance = TimePickerFragment()

            val args = Bundle(2)
            args.putInt(ARG_PARAM_HOUR, hour)
            args.putInt(ARG_PARAM_MINUTE, minute)

            instance.arguments = args

            return instance
        }
    }
}