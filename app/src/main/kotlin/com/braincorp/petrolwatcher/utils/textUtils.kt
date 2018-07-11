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