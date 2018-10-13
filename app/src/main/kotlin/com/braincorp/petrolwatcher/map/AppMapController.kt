package com.braincorp.petrolwatcher.map

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.util.Log
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import java.util.*

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
     * @param onCurrentLocationFoundListener the callback to be triggered when
     *                                       the current location is found
     */
    override fun zoomToDeviceLocation(map: GoogleMap, context: Context,
                                      onCurrentLocationFoundListener: OnCurrentLocationFoundListener) {
        try {
            val client = LocationServices.getFusedLocationProviderClient(context)
            val locationResult = client.lastLocation
            locationResult.addOnCompleteListener {
                val zoomLevel = 15f
                val taskResult = it.result
                taskResult?.let { result ->
                    val location = LatLng(result.latitude, result.longitude)
                    // Address, city, country and locale are not necessary here
                    onCurrentLocationFoundListener.onCurrentLocationFound(address = "",
                            city = "",
                            country = "",
                            latLng = location,
                            locale = Locale.getDefault())
                    val cameraPosition = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
                    map.moveCamera(cameraPosition)
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, e.message, e)
        }
    }

    /**
     * Gets the distance between 2 points, in metres
     *
     * @param a the start point
     * @param b the end point
     *
     * @return the distance in metres
     */
    override fun getDistanceInMetres(a: LatLng, b: LatLng): Float {
        val startLatitude = a.latitude
        val startLongitude = a.longitude

        val endLatitude = b.latitude
        val endLongitude = b.longitude

        val results = FloatArray(4)

        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)

        return results[0]
    }

    /**
     * Gets a locale from a place
     *
     * @param context the Android context
     * @param latLng the place
     *
     * @return the locale
     */
    override fun getLocaleFromLatLng(context: Context, latLng: LatLng): Locale {
        val geocoder = Geocoder(context)
        val maxResults = 1

        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResults)
        val address = addresses[0]
        val countryCode = address.countryCode

        val availableLocales = Locale.getAvailableLocales()

        return availableLocales.first { it.country == countryCode }
    }

    /**
     * Gets data related to a location
     *
     * @param context the Android context
     * @param location the location
     *
     * @return data such as address, coordinates and locale
     */
    override fun getDataFromLocation(context: Context, location: Location): Address {
        val geocoder = Geocoder(context)
        val latLng = LatLng(location.latitude, location.longitude)

        val maxResults = 1
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResults)

        return addresses[0]
    }

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCurrentLocationFoundListener the callback to be triggered
     *                                       when all data belonging to the
     *                                       location is found
     */
    override fun getCurrentLocation(context: Context,
                                    onCurrentLocationFoundListener: OnCurrentLocationFoundListener) {
        getCurrentLocation(context, OnCompleteListener {
            val location = it.result ?: return@OnCompleteListener

            val locationData = getDataFromLocation(context, location)
            val address = locationData.getAddressLine(0)
            val latLng = LatLng(locationData.latitude, locationData.longitude)
            val locale = getLocaleFromLatLng(context, latLng)
            val city = locationData.locality
            val country = locationData.countryName

            onCurrentLocationFoundListener.onCurrentLocationFound(address, city, country, latLng, locale)
        })
    }

    /**
     * Gets the city from a place
     *
     * @param context the Android context
     * @param place the place
     *
     * @return the city
     */
    override fun getCityFromPlace(context: Context, place: Place): String {
        val geocoder = Geocoder(context)
        val maxResults = 1
        val address = geocoder.getFromLocation(place.latLng.latitude,
                place.latLng.longitude,
                maxResults)[0]
        return address.locality
    }

    /**
     * Gets the country from a place
     *
     * @param context the Android context
     * @param place the place
     *
     * @return the country
     */
    override fun getCountryFromPlace(context: Context, place: Place): String {
        val geocoder = Geocoder(context)
        val maxResults = 1
        val address = geocoder.getFromLocation(place.latLng.latitude,
                place.latLng.longitude,
                maxResults)[0]
        return address.countryName
    }

}