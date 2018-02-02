package com.braincorp.petrolwatcher.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.model.FuelType

class StationLocatorService : IntentService(SERVICE_NAME) {

    companion object {
        private const val DEFAULT_RADIUS = 200f // = 200m
        const val MAX_RADIUS = 2000f // = 2km

        private const val EXTRA_FUEL = "fuel"
        private const val EXTRA_MAX_PRICE = "max_price"
        private const val EXTRA_RADIUS = "radius"

        private const val SERVICE_NAME = "StationLocatorService"

        fun getIntent(context: Context,
                      fuelType: FuelType,
                      maxPrice: Float,
                      radius: Float = DEFAULT_RADIUS) : Intent {
            val intent = Intent(context, StationLocatorService::class.java)
            intent.putExtra(EXTRA_FUEL, fuelType)
            intent.putExtra(EXTRA_MAX_PRICE, maxPrice)
            intent.putExtra(EXTRA_RADIUS, radius)
            return intent
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onHandleIntent(intent: Intent?) {
        val fuel = intent?.getSerializableExtra(EXTRA_FUEL) as Pair<FuelType, FuelQuality>
        val maxPrice = intent.getFloatExtra(EXTRA_MAX_PRICE, 0f)
        val radius = if (intent.getFloatExtra(EXTRA_RADIUS, DEFAULT_RADIUS) > MAX_RADIUS) {
            MAX_RADIUS
        } else {
            intent.getFloatExtra(EXTRA_RADIUS, DEFAULT_RADIUS)
        }

        locateStations(fuel, maxPrice, radius)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun locateStations(fuel: Pair<FuelType, FuelQuality>, maxPrice: Float, radius: Float) {
        TODO("not implemented")
    }

}