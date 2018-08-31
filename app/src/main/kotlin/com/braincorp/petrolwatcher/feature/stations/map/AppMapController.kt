package com.braincorp.petrolwatcher.feature.stations.map

import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

/**
 * The map controller used in the app
 */
class AppMapController : MapController {

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

}