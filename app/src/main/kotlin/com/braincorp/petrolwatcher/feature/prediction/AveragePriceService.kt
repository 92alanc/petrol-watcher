package com.braincorp.petrolwatcher.feature.prediction

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice

/**
 * A background service that updates average fuel prices
 */
class AveragePriceService : IntentService("average-price-service") {

    companion object {
        private const val KEY_AVERAGE_PRICES = "average_prices"

        fun getIntent(context: Context, averagePrices: ArrayList<AveragePrice>): Intent {
            return Intent(context, AveragePriceService::class.java)
                    .putExtra(KEY_AVERAGE_PRICES, averagePrices)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return

        val averagePrices = intent.getParcelableArrayListExtra<AveragePrice>(KEY_AVERAGE_PRICES)
        averagePrices.forEach {
            DependencyInjection.databaseManager.saveAveragePrice(it)
        }
    }

}