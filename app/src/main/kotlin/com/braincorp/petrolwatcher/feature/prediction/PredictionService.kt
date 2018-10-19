package com.braincorp.petrolwatcher.feature.prediction

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionsReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.utils.showNotificationForPredictions

/**
 * A background service that watches for fuel
 * price predictions and notifies the user on
 * receiving new predictions
 */
class PredictionService : Service(), OnPredictionsReadyListener {

    override fun onCreate() {
        super.onCreate()
        DependencyInjection.databaseManager.fetchPredictions(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * Function triggered when new predictions are ready
     *
     * @param predictions the predictions
     */
    override fun onPredictionsReady(predictions: ArrayList<Prediction>) {
        applicationContext.showNotificationForPredictions(predictions)
    }

}