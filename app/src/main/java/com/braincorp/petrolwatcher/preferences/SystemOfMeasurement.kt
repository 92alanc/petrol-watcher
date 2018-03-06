package com.braincorp.petrolwatcher.preferences

import android.content.Context
import com.braincorp.petrolwatcher.R

enum class SystemOfMeasurement : Configuration {

    IMPERIAL,
    METRIC;

    override fun getText(context: Context): String {
        val stringRes = when (this) {
            IMPERIAL -> R.string.imperial
            METRIC -> R.string.metric
        }

        return context.getString(stringRes)
    }

}