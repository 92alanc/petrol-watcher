package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.location.Location
import android.net.Uri
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.util.Log
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

    /**
     * Adds petrol stations to a map
     *
     * @param map the map
     * @param petrolStations the petrol stations
     * @param onMarkerClickListener the callback to be triggered
     *                              when a petrol station marker
     *                              is clicked
     */
    override fun addPetrolStationsToMap(map: GoogleMap,
                                        petrolStations: ArrayList<PetrolStation>,
                                        onMarkerClickListener: GoogleMap.OnMarkerClickListener) {
        petrolStations.forEach {
            val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_station)
            map.addMarker(MarkerOptions().title(it.name)
                    .position(it.latLng)
                    .icon(icon)
                    .draggable(false))
        }
        map.setOnMarkerClickListener(onMarkerClickListener)
    }

    /**
     * Gets an intent for directions to a given address
     *
     * @param destination the destination address
     *
     * @return the intent with the directions
     */
    override fun getDirectionsIntent(destination: String): Intent {
        val baseUrl = "https://www.google.com/maps/dir/?api=1"
        val destinationParam = "destination=${Uri.encode(destination)}"
        val travelModeParam = "travelmode=driving"
        val uri = Uri.parse("$baseUrl&$destinationParam&$travelModeParam")
        return Intent(ACTION_VIEW, uri)
    }

    /**
     * Zooms the map to the device's current location
     *
     * @param map the map
     * @param context the Android context
     */
    override fun zoomToDeviceLocation(map: GoogleMap, context: Context) {
        try {
            val client = LocationServices.getFusedLocationProviderClient(context)
            val locationResult = client.lastLocation
            locationResult.addOnCompleteListener {
                val zoomLevel = 15f
                val location = LatLng(it.result.latitude, it.result.longitude)
                val cameraPosition = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
                map.moveCamera(cameraPosition)
            }
        } catch (e: SecurityException) {
            Log.e(TAG, e.message, e)
        }
    }

}