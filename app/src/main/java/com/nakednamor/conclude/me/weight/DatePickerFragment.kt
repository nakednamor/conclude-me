package com.nakednamor.conclude.me.weight

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.time.LocalDate

const val ARG_PARAM_YEAR = "year"
const val ARG_PARAM_MONTH = "month"
const val ARG_PARAM_DAY = "day"
const val RESULT_KEY = "pickedDate" // TODO rename to RESULT_KEY_DATE

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            year = it.getInt(ARG_PARAM_YEAR)
            month = it.getInt(ARG_PARAM_MONTH)
            day = it.getInt(ARG_PARAM_DAY)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val act = activity ?: throw RuntimeException("no activity found")
        val now = LocalDate.now()
        return DatePickerDialog(
            act,
            this,
            year ?: now.year,
            month ?: now.monthValue,
            day ?: now.dayOfMonth
        )
    }

    override fun onDateSet(view: DatePicker?, pickedYear: Int, pickedMonth: Int, pickedDay: Int) {
        setFragmentResult(
            RESULT_KEY,
            bundleOf(
                ARG_PARAM_YEAR to pickedYear,
                ARG_PARAM_MONTH to pickedMonth,
                ARG_PARAM_DAY to pickedDay
            )
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(year: Int, month: Int, dayOfMonth: Int): DatePickerFragment {
            val instance = DatePickerFragment()

            val args = Bundle(3)
            args.putInt(ARG_PARAM_YEAR, year)
            args.putInt(ARG_PARAM_MONTH, month)
            args.putInt(ARG_PARAM_DAY, dayOfMonth)

            instance.arguments = args

            return instance
        }
    }
}