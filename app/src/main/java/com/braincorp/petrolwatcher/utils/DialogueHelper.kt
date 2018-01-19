package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.braincorp.petrolwatcher.R

fun Context.showErrorDialogue(@StringRes message: Int) {
    AlertDialog.Builder(this).setTitle(R.string.error)
            .setMessage(message)
            .setIcon(R.drawable.ic_error)
            .setNeutralButton(R.string.ok, null)
            .show()
}