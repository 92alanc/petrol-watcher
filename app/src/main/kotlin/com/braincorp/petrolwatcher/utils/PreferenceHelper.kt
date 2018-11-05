package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * A shared preferences helper
 */
class PreferenceHelper(context: Context) {

    private companion object {
        const val FILE_NAME = "preferences"
        const val KEY_IS_PREDICTION_NOTIFICATION_VIEWED = "is_prediction_notification_viewed"
    }

    private val preferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)

    /**
     * Determines whether the prediction notification
     * has been viewed by the user
     *
     * @return true if positive, otherwise false
     */
    fun isPredictionNotificationViewed(): Boolean {
        return preferences.getBoolean(KEY_IS_PREDICTION_NOTIFICATION_VIEWED, false)
    }

    /**
     * Sets the prediction notification viewed value
     *
     * @param isNotificationViewed the new value
     */
    fun setNotificationViewed(isNotificationViewed: Boolean) {
        preferences.edit().putBoolean(KEY_IS_PREDICTION_NOTIFICATION_VIEWED,
                isNotificationViewed).apply()
    }

}