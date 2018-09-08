package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.location.Location
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.tasks.OnCompleteListener

/**
 * A map controller
 */
interface MapController {

    /**
     * Starts a map
     *
     * @param fragmentManager the fragment manager
     * @param mapId the ID of the map
     * @param onMapReadyCallback the callback to be triggered
     *                           once the map is ready
     */
    fun startMap(fragmentManager: FragmentManager,
                 @IdRes mapId: Int,
                 onMapReadyCallback: OnMapReadyCallback)

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCompleteListener the callback to be triggered
     *                           once the location is ready
     */
    fun getCurrentLocation(context: Context, onCompleteListener: OnCompleteListener<Location>)

}