package com.braincorp.petrolwatcher.utils

import java.util.*

fun floatToCurrencyString(locale: Locale, value: Float): String {
    val currency = Currency.getInstance(locale).symbol
    return "$currency $value"
}
