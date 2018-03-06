package com.braincorp.petrolwatcher.preferences

import android.content.Context
import com.braincorp.petrolwatcher.R

enum class MapTheme : Configuration {

    LIGHT,
    DARK;

    override fun getText(context: Context): String {
        val stringRes = when (this) {
            LIGHT -> R.string.theme_light
            DARK -> R.string.theme_dark
        }

        return context.getString(stringRes)
    }

}