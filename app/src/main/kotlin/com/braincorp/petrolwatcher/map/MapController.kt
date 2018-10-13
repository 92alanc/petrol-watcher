package com.braincorp.petrolwatcher.map

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Location
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import java.util.*

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
     * @param onCurrentLocationFoundListener the callback to be triggered when
     *                                       the current location is found
     */
    fun zoomToDeviceLocation(map: GoogleMap,
                             context: Context,
                             onCurrentLocationFoundListener: OnCurrentLocationFoundListener)

    /**
     * Gets the distance between 2 points, in metres
     *
     * @param a the start point
     * @param b the end point
     *
     * @return the distance in metres
     */
    fun getDistanceInMetres(a: LatLng, b: LatLng): Float

    /**
     * Gets a locale from a latitude/longitude coordinate
     *
     * @param context the Android context
     * @param latLng the coordinates
     *
     * @return the locale
     */
    fun getLocaleFromLatLng(context: Context, latLng: LatLng): Locale

    /**
     * Gets data related to a location
     *
     * @param context the Android context
     * @param location the location
     *
     * @return data such as address, coordinates and locale
     */
    fun getDataFromLocation(context: Context, location: Location): Address

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCurrentLocationFoundListener the callback to be triggered
     *                                       when all data belonging to the
     *                                       location is found
     */
    fun getCurrentLocation(context: Context,
                           onCurrentLocationFoundListener: OnCurrentLocationFoundListener)

    /**
     * Gets the city from a place
     *
     * @param context the Android context
     * @param place the place
     *
     * @return the city
     */
    fun getCityFromPlace(context: Context, place: Place): String

    /**
     * Gets the country from a place
     *
     * @param context the Android context
     * @param place the place
     *
     * @return the country
     */
    fun getCountryFromPlace(context: Context, place: Place): String

}