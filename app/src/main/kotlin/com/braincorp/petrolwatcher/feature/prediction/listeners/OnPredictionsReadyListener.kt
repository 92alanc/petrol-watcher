package com.braincorp.petrolwatcher.feature.prediction.listeners

import com.braincorp.petrolwatcher.feature.prediction.model.Prediction

/**
 * A callback for new predictions
 */
interface OnPredictionsReadyListener {
    /**
     * Function triggered when new predictions are ready
     *
     * @param predictions the predictions
     */
    fun onPredictionsReady(predictions: ArrayList<Prediction>)
}