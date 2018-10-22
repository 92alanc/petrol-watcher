package com.braincorp.petrolwatcher.feature.prediction

import android.app.job.JobParameters
import android.app.job.JobService
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionsReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.utils.showNotificationForPredictions

/**
 * A background service that watches for fuel
 * price predictions and notifies the user on
 * receiving new predictions
 */
class PredictionService : JobService(), OnPredictionsReadyListener {

    override fun onStartJob(params: JobParameters): Boolean {
        DependencyInjection.databaseManager.fetchPredictions(this)
        jobFinished(params, true)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        return true
    }

    /**
     * Function triggered when new predictions are ready
     *
     * @param predictions the predictions
     */
    override fun onPredictionsReady(predictions: ArrayList<Prediction>) {
        applicationContext.showNotificationForPredictions(predictions)
    }

}