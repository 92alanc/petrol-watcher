package com.braincorp.petrolwatcher.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.braincorp.petrolwatcher.model.SystemOfMeasurement

class PreferenceManager(context: Context) {

    private companion object {
        const val FILE_NAME = "preferences"

        const val KEY_SYSTEM_OF_MEASUREMENT = "system_of_measurement"

        const val IMPERIAL_INT = 1
        const val METRIC_INT = 2
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
    }

    fun getSystemOfMeasurement(): SystemOfMeasurement {
        val result = sharedPreferences.getInt(KEY_SYSTEM_OF_MEASUREMENT, METRIC_INT)
        return when (result) {
            IMPERIAL_INT -> SystemOfMeasurement.IMPERIAL
            else -> SystemOfMeasurement.METRIC
        }
    }

    fun setSystemOfMeasurement(systemOfMeasurement: SystemOfMeasurement) {
        val intValue = when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> IMPERIAL_INT
            SystemOfMeasurement.METRIC -> METRIC_INT
        }
        sharedPreferences.edit().putInt(KEY_SYSTEM_OF_MEASUREMENT, intValue).apply()
    }

}