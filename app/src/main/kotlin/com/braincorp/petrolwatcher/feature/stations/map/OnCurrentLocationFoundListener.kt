package com.braincorp.petrolwatcher.feature.stations.map

import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * Callback for current address
 */
interface OnCurrentLocationFoundListener {

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
    fun onCurrentLocationFound(address: String,
                               city: String,
                               country: String,
                               latLng: LatLng,
                               locale: Locale)

}