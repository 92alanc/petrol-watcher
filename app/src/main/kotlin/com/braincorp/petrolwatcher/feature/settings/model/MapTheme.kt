package com.braincorp.petrolwatcher.feature.settings.model

import android.content.Context
import com.braincorp.petrolwatcher.R

enum class MapTheme : Configuration {

    LIGHT,
    DARK;

    companion object {
        fun toMap(): Map<Configuration, Int> {
            return mapOf(Pair(LIGHT, R.drawable.map_light),
                         Pair(DARK, R.drawable.map_dark))
        }
    }

    override fun findByIndex(index: Int): Configuration {
        return when (index) {
            LIGHT.ordinal -> LIGHT
            DARK.ordinal -> DARK
            else -> LIGHT
        }
    }

    override fun getDescription(context: Context): String {
        val stringRes = when (this) {
            LIGHT -> R.string.theme_light
            DARK -> R.string.theme_dark
        }

        return context.getString(stringRes)
    }

    override fun getName(context: Context): String = context.getString(R.string.map_theme)

}