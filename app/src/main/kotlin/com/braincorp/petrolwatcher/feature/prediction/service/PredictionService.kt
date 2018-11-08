package com.braincorp.petrolwatcher.feature.prediction.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.utils.PreferenceHelper
import com.braincorp.petrolwatcher.utils.normaliseArea
import com.braincorp.petrolwatcher.utils.showNotificationForPrediction
import java.util.*

/**
 * Background service that watches for new average price
 * predictions in the current location (city/country)
 */
class PredictionService : IntentService("prediction"),
        OnPredictionReadyListener {

    companion object {
        private const val KEY_CITY = "city"
        private const val KEY_COUNTRY = "country"
        private const val KEY_LOCALE = "locale"
        private const val TAG = "petrol_watcher"

        fun getIntent(context: Context,
                      city: String,
                      country: String,
                      locale: Locale): Intent {
            return Intent(context, PredictionService::class.java)
                    .putExtra(KEY_CITY, city)
                    .putExtra(KEY_COUNTRY, country)
                    .putExtra(KEY_LOCALE, locale.toLanguageTag())
        }
    }

    private lateinit var area: String
    private lateinit var locale: Locale

    override fun onHandleIntent(intent: Intent) {
        Log.d(TAG, "Prediction service started")
        with(intent) {
            val city = getStringExtra(KEY_CITY)
            val country = getStringExtra(KEY_COUNTRY)
            area = normaliseArea(city, country)
            locale = Locale.forLanguageTag(getStringExtra(KEY_LOCALE))
        }
        DependencyInjection.databaseManager.fetchPrediction(area, this)
    }

    /**
     * Function triggered when a new prediction is ready
     *
     * @param prediction the prediction
     */
    override fun onPredictionReady(prediction: Prediction) {
        Log.d(TAG, "Prediction ready")

        applicationContext.let {
            val preferenceHelper = PreferenceHelper(it)
            if (!preferenceHelper.isPredictionNotificationViewed())
                it.showNotificationForPrediction(prediction, locale)
        }
    }

}