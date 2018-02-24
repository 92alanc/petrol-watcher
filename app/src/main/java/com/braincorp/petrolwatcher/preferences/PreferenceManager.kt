package com.braincorp.petrolwatcher.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.braincorp.petrolwatcher.model.SystemOfMeasurement

class PreferenceManager(context: Context) {

    private companion object {
        const val FILE_NAME = "preferences"

        const val KEY_SYSTEM_OF_MEASUREMENT = "system_of_measurement"
        const val KEY_THEME = "theme"

        const val SYSTEM_IMPERIAL = 1
        const val SYSTEM_METRIC = 2
        const val THEME_LIGHT = 3
        const val THEME_DARK = 4

    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
    }

    fun getSystemOfMeasurement(): SystemOfMeasurement {
        val result = sharedPreferences.getInt(KEY_SYSTEM_OF_MEASUREMENT, SYSTEM_METRIC)
        return when (result) {
            SYSTEM_IMPERIAL -> SystemOfMeasurement.IMPERIAL
            else -> SystemOfMeasurement.METRIC
        }
    }

    fun setSystemOfMeasurement(systemOfMeasurement: SystemOfMeasurement) {
        val intValue = when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> SYSTEM_IMPERIAL
            SystemOfMeasurement.METRIC -> SYSTEM_METRIC
        }
        sharedPreferences.edit().putInt(KEY_SYSTEM_OF_MEASUREMENT, intValue).apply()
    }

    // TODO: implement get/set theme

}