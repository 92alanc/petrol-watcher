package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.util.Log
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction

/**
 * Shows a notification for fuel price predictions
 *
 * @param predictions the predictions
 */
fun Context.showNotificationForPredictions(predictions: ArrayList<Prediction>) {
    Log.d("ALAN", "New predictions!!! Size: ${predictions.size}}")
    // TODO: implement
}