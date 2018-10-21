package com.braincorp.petrolwatcher.feature.prediction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.database.OnAveragePriceFoundListener
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.utils.hasLocationPermission
import com.google.android.gms.maps.model.LatLng
import java.util.*
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.SATURDAY

/**
 * A receiver for date change events
 */
class DateReceiver : BroadcastReceiver(),
        OnCurrentLocationFoundListener,
        OnAveragePriceFoundListener {

    private lateinit var context: Context

    override fun onReceive(context: Context, intent: Intent?) {
        val isLoggedIn = DependencyInjection.authenticator.isUserSignedIn()
        if (intent?.action != Intent.ACTION_DATE_CHANGED || !isLoggedIn) return

        this.context = context
        val today = Calendar.getInstance().get(DAY_OF_WEEK)

        if (today == SATURDAY)
            prepareIntentForService()
    }

    override fun onCurrentLocationFound(address: String,
                                        city: String,
                                        country: String,
                                        latLng: LatLng,
                                        locale: Locale) {
        DependencyInjection.databaseManager.getAveragePricesForCityAndCountry(city,
                country,
                this)
    }

    override fun onAveragePriceFound(averagePrice: AveragePrice) {
        // Not necessary
    }

    override fun onAveragePricesFound(averagePrices: ArrayList<AveragePrice>) {
        val intent = AveragePriceService.getIntent(context, averagePrices)
        context.startService(intent)
    }

    private fun prepareIntentForService() {
        if (!context.hasLocationPermission()) return
        DependencyInjection.mapController.getCurrentLocation(context, this)
    }

}