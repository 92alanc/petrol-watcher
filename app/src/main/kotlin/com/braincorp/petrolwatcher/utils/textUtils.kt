package com.braincorp.petrolwatcher.utils

import android.support.design.widget.TextInputEditText

/**
 * Shows a text field error
 *
 * @param field the text field
 * @param message the error message
 */
fun showFieldError(field: TextInputEditText, message: String) {
    field.error = message
    field.requestFocus()
}

/**
 * Sets a generic value to an EditText
 *
 * @param editText the EditText
 * @param value the value
 */
fun <T> setEditTextValue(editText: TextInputEditText, value: T) {
    value.let {
        if ((it is Int && it > 0) || (it is Float && it > 0f))
            editText.setText(it.toString())
        else if (it is String && it.isNotBlank())
            editText.setText(it)
    }
}