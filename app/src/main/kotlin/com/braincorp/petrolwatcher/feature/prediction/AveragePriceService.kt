package com.braincorp.petrolwatcher.feature.prediction

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.database.OnAveragePriceFoundListener
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.utils.hasLocationPermission
import com.google.android.gms.maps.model.LatLng
import java.util.*

class AveragePriceService : Service(),
                            OnCurrentLocationFoundListener,
                            OnAveragePriceFoundListener {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, AveragePriceService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        if (DependencyInjection.authenticator.isUserSignedIn()) {
            applicationContext.let {
                if (hasLocationPermission()) {
                    DependencyInjection.mapController.getCurrentLocation(it,
                                                                         this)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * Function to be triggered when the current address
     * is found
     *
     * @param address the address
     * @param city the city
     * @param country the country
     * @param latLng the latitude and longitude
     * @param locale the locale
     */
    override fun onCurrentLocationFound(address: String,
                                        city: String,
                                        country: String,
                                        latLng: LatLng,
                                        locale: Locale) {
        DependencyInjection.databaseManager.getAveragePricesForCityAndCountry(city, country,
                                                                              this)
    }

    /**
     * Callback triggered when the average price is found
     *
     * @param averagePrice the average price
     */
    override fun onAveragePriceFound(averagePrice: AveragePrice) {
        // Not necessary
    }

    /**
     * Callback triggered when the average prices for a city
     * and country are found
     *
     * @param averagePrices the average fuel prices
     */
    override fun onAveragePricesFound(averagePrices: ArrayList<AveragePrice>) {
        // TODO: save average prices and start prediction service
    }

}