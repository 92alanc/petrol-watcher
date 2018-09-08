package com.braincorp.petrolwatcher.feature.stations.map

import com.google.android.gms.maps.model.LatLng

/**
 * Callback for current address
 */
interface OnCurrentLocationFoundListener {

    /**
     * Function to be triggered when the current address
     * is found
     *
     * @param address the address
     * @param latLng the latitude and longitude
     */
    fun onCurrentLocationFound(address: String, latLng: LatLng)

}