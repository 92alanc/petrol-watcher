package com.braincorp.petrolwatcher.utils

import java.util.*
import java.util.Calendar.*

/**
 * Gets the time left until Saturday
 *
 * @return the time, in milliseconds
 */
fun getTimeLeftUntilSaturday(): Long {
    val calendar = Calendar.getInstance()

    if (calendar.get(DAY_OF_WEEK) == SATURDAY) {
        calendar.add(DAY_OF_WEEK, 7)
    } else {
        with(calendar) {
            val daysDiff = SATURDAY - get(DAY_OF_WEEK)
            add(DAY_OF_WEEK, daysDiff)
            set(HOUR_OF_DAY, 0)
            set(MINUTE, 0)
            set(SECOND, 0)
        }
    }

    return calendar.timeInMillis
}

/**
 * Gets a week's time in milliseconds
 *
 * @return a week's time in milliseconds
 */
fun getWeekInMillis(): Long {
    return 7L * 24L * 60L * 60L * 1000L
}