package com.braincorp.petrolwatcher.feature.settings.model

import android.content.Context
import com.braincorp.petrolwatcher.R

enum class SystemOfMeasurement : Configuration {

    IMPERIAL,
    METRIC;

    companion object {
        fun toMap(): Map<Configuration, Int?> {
            return mapOf(Pair(IMPERIAL, null), Pair(
                    METRIC, null))
        }
    }

    override fun findByIndex(index: Int): Configuration {
        return when (index) {
            IMPERIAL.ordinal -> IMPERIAL
            METRIC.ordinal -> METRIC
            else -> METRIC
        }
    }

    override fun getDescription(context: Context): String {
        val stringRes = when (this) {
            IMPERIAL -> R.string.imperial
            METRIC -> R.string.metric
        }

        return context.getString(stringRes)
    }

    override fun getName(context: Context): String = context.getString(R.string.system_of_measurement)

}