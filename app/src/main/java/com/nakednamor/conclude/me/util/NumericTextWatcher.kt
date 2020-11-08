package com.nakednamor.conclude.me.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NumericTextWatcher(
    private val editText: EditText,
    private val decimalDigits: Int = 0,
    private val errorMessage: String
) : TextWatcher {

    private val noDecimalDigitsPattern = "[1-9][0-9]*".toRegex()
    private val decimalDigitsPattern = "[1-9][0-9]*(.[0-9]{1,$decimalDigits})?".toRegex()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            val pattern = if (decimalDigits == 0) noDecimalDigitsPattern else decimalDigitsPattern
            if (!it.matches(pattern)) {
                editText.error = errorMessage
            } else {
                editText.error = null
            }
        }
    }
}