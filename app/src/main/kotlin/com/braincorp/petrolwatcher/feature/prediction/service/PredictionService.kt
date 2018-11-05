package com.braincorp.petrolwatcher.feature.prediction.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionsReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.utils.PreferenceHelper
import com.braincorp.petrolwatcher.utils.showNotificationForPredictions
import java.util.*

/**
 * Background service that watches for new average price
 * predictions in the current location (city/country)
 */
class PredictionService : IntentService("prediction"),
        OnPredictionsReadyListener {

    companion object {
        private const val KEY_CITY = "city"
        private const val KEY_COUNTRY = "country"
        private const val KEY_LOCALE = "locale"

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

    private lateinit var city: String
    private lateinit var country: String
    private lateinit var locale: Locale

    override fun onHandleIntent(intent: Intent) {
        with(intent) {
            city = getStringExtra(KEY_CITY)
            country = getStringExtra(KEY_COUNTRY)
            locale = Locale.forLanguageTag(getStringExtra(KEY_LOCALE))
        }
        DependencyInjection.databaseManager.fetchPredictions(this)
    }

    /**
     * Function triggered when new predictions are ready
     *
     * @param predictions the predictions
     */
    override fun onPredictionsReady(predictions: ArrayList<Prediction>) {
        val localPredictions = predictions.filter {
            it.city == city && it.country == country
        }

        applicationContext.let {
            val preferenceHelper = PreferenceHelper(it)
            if (preferenceHelper.isPredictionNotificationViewed())
                it.showNotificationForPredictions(localPredictions, locale)
        }
    }

}