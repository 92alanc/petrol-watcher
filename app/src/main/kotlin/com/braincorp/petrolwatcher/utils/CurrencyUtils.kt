package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import java.util.*

@Suppress("DEPRECATION")
fun Context.floatToCurrencyString(value: Float): String {
    val locale = if (SDK_INT >= N) resources.configuration.locales.get(0)
    else resources.configuration.locale

    val currency = Currency.getInstance(locale).symbol
    return "$currency $value"
}
