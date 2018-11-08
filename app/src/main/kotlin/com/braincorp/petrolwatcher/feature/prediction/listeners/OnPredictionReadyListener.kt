package com.braincorp.petrolwatcher.feature.prediction.listeners

import com.braincorp.petrolwatcher.feature.prediction.model.Prediction

/**
 * A callback for new predictions
 */
interface OnPredictionReadyListener {
    /**
     * Function triggered when a new prediction is ready
     *
     * @param prediction the prediction
     */
    fun onPredictionReady(prediction: Prediction)
}