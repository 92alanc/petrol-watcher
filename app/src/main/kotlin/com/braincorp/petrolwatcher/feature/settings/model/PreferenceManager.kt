package com.braincorp.petrolwatcher.feature.settings.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private companion object {
        const val FILE_NAME = "preferences"

        const val KEY_SYSTEM_OF_MEASUREMENT = "system_of_measurement"
        const val KEY_MAP_THEME = "map_theme"

        const val SYSTEM_IMPERIAL = 1
        const val SYSTEM_METRIC = 2
        const val MAP_THEME_LIGHT = 3
        const val MAP_THEME_DARK = 4

    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)
    }

    fun getSystemOfMeasurement(): SystemOfMeasurement {
        val result = sharedPreferences.getInt(KEY_SYSTEM_OF_MEASUREMENT,
                                              SYSTEM_METRIC)
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

    fun getMapTheme(): MapTheme {
        val result = sharedPreferences.getInt(KEY_MAP_THEME,
                                              MAP_THEME_LIGHT)
        return when (result) {
            MAP_THEME_LIGHT -> MapTheme.LIGHT
            MAP_THEME_DARK -> MapTheme.DARK
            else -> MapTheme.LIGHT
        }
    }

    fun setMapTheme(mapTheme: MapTheme) {
        val intValue = when (mapTheme) {
            MapTheme.LIGHT -> MAP_THEME_LIGHT
            MapTheme.DARK -> MAP_THEME_DARK
        }
        sharedPreferences.edit().putInt(KEY_MAP_THEME, intValue).apply()
    }

}