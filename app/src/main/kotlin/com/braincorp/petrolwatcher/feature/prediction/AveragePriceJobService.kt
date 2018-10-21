package com.braincorp.petrolwatcher.feature.prediction

import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build.VERSION_CODES.O
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.database.OnAveragePriceFoundListener
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.utils.hasLocationPermission
import com.google.android.gms.maps.model.LatLng
import java.util.*

@TargetApi(O)
class AveragePriceJobService : JobService(),
        OnCurrentLocationFoundListener,
        OnAveragePriceFoundListener {

    override fun onStartJob(params: JobParameters): Boolean {
        if (DependencyInjection.authenticator.isUserSignedIn()) {
            applicationContext.let {
                if (hasLocationPermission()) {
                    DependencyInjection.mapController.getCurrentLocation(it,
                            this)
                }
            }
        }
        jobFinished(params, true)
        return true
    }

    override fun onStopJob(params: JobParameters) = true

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
        // TODO: save average prices
    }

}