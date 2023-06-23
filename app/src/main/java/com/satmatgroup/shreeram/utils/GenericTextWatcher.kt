package com.satmatgroup.shreeram.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class GenericTextWatcher(private val etNext: EditText, private val etPrev: EditText) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        if (text.length == 1) etNext.requestFocus() else if (text.length == 0) etPrev.requestFocus()
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

}