package com.braincorp.petrolwatcher.utils

import android.annotation.TargetApi
import android.os.Build.VERSION_CODES.O
import java.util.*
import java.util.Calendar.*

/**
 * Gets the time left until the coming Saturday.
 * If the current day is already Saturday, the
 * function will return the time left until the
 * next one
 *
 * @return the time in milliseconds
 */
@TargetApi(O)
fun getTimeUntilSaturday(): Long {
    val now = Calendar.getInstance()

    val saturday = Calendar.getInstance().apply {
        if (now.get(DAY_OF_WEEK) < SATURDAY)
            setWeekDate(now.weekYear, now.get(WEEK_OF_YEAR), SATURDAY)
        else
            setWeekDate(now.weekYear, now.get(WEEK_OF_YEAR) + 1, SATURDAY)

        set(HOUR_OF_DAY, 0)
        set(MINUTE, 0)
        set(SECOND, 0)
    }

    return saturday.timeInMillis - now.timeInMillis
}

/**
 * Gets a week's time in milliseconds
 *
 * @return a week's time in milliseconds
 */
fun getWeekInMillis(): Long {
    return 7L * 24L * 60L * 60L * 1000L
}