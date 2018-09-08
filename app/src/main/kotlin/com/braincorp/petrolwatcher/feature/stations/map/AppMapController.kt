package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.location.Location
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.tasks.OnCompleteListener

/**
 * The map controller used in the app
 */
class AppMapController : MapController {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    /**
     * Starts a map
     *
     * @param fragmentManager the fragment manager
     * @param mapId the ID of the map
     * @param onMapReadyCallback the callback to be triggered
     *                           once the map is ready
     */
    override fun startMap(fragmentManager: FragmentManager,
                          @IdRes mapId: Int,
                          onMapReadyCallback: OnMapReadyCallback) {
        val mapFragment = fragmentManager.findFragmentById(mapId) as SupportMapFragment
        mapFragment.getMapAsync(onMapReadyCallback)
    }

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCompleteListener the callback to be triggered
     *                           once the location is ready
     */
    override fun getCurrentLocation(context: Context,
                                    onCompleteListener: OnCompleteListener<Location>) {
        try {
            val client = LocationServices.getFusedLocationProviderClient(context)
            val locationResult = client.lastLocation
            locationResult.addOnCompleteListener(onCompleteListener)
        } catch (e: SecurityException) {
            Log.e(TAG, e.message, e)
        }
    }

}