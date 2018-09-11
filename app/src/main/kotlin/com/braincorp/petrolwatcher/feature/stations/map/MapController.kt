package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.maps.GoogleMap
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

    /**
     * Adds petrol stations to a map
     *
     * @param map the map
     * @param petrolStations the petrol stations
     * @param onMarkerClickListener the callback to be triggered
     *                              when a petrol station marker
     *                              is clicked
     */
    fun addPetrolStationsToMap(map: GoogleMap,
                               petrolStations: ArrayList<PetrolStation>,
                               onMarkerClickListener: GoogleMap.OnMarkerClickListener)

    /**
     * Gets an intent for directions to a given address
     *
     * @param destination the destination address
     *
     * @return the intent with the directions
     */
    fun getDirectionsIntent(destination: String): Intent

    /**
     * Zooms the map to the device's current location
     *
     * @param map the map
     * @param context the Android context
     */
    fun zoomToDeviceLocation(map: GoogleMap, context: Context)

}