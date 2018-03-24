package com.braincorp.petrolwatcher.feature.map.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.location.Location
import android.net.Uri
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.settings.model.MapTheme
import com.braincorp.petrolwatcher.feature.settings.model.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.OnCompleteListener

private const val DEFAULT_ZOOM = 15f
private const val LOG_TAG = "utils"

fun Context.getCurrentLocation(onCompleteListener: OnCompleteListener<Location>) {
    try {
        val client = LocationServices.getFusedLocationProviderClient(this)
        val locationResult = client.lastLocation
        locationResult.addOnCompleteListener(onCompleteListener)
    } catch (e: SecurityException) {
        Log.e(javaClass.name, e.message)
    }
}

fun GoogleMap.zoomToLocation(latLng: LatLng) {
    val cameraPosition = CameraUpdateFactory.newLatLngZoom(latLng,
                                                           DEFAULT_ZOOM)
    moveCamera(cameraPosition)
}

fun AppCompatActivity.loadMapWithCurrentLocation(map: GoogleMap?) {
    if (map == null) return

    try {
        map.isMyLocationEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        zoomToDeviceLocation(map)
    } catch (e: SecurityException) {
        Log.e(LOG_TAG, e.message)
    }
}

fun loadMapWithoutCurrentLocation(map: GoogleMap?) {
    if (map == null) return

    try {
        map.isMyLocationEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
    } catch (e: SecurityException) {
        Log.e(LOG_TAG, e.message)
    }
}

fun Context.showCurrentLocation(map: GoogleMap?) {
    getCurrentLocation(OnCompleteListener {
        val location = it.result
        val latLng = LatLng(location.latitude, location.longitude)
        map?.zoomToLocation(latLng)
    })
}

fun Context.showDirections(destinationAddress: String) {
    val baseUrl = "https://www.google.com/maps/dir/?api=1"
    val destination = "destination=${Uri.encode(destinationAddress)}"
    val travelMode = "travelmode=driving"
    val uri = Uri.parse("$baseUrl&$destination&$travelMode")
    val intent = Intent(ACTION_VIEW, uri)
    startActivity(intent)
}

fun Context.applyMapTheme(map: GoogleMap?) {
    val preferenceManager = PreferenceManager(
            this)
    val mapTheme = preferenceManager.getMapTheme()
    val res = when (mapTheme) {
        MapTheme.LIGHT -> R.raw.map_light
        MapTheme.DARK -> R.raw.map_dark
    }
    map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, res))
}

fun getDistanceInMetres(start: LatLng, end: LatLng): Float {
    val startLatitude = start.latitude
    val startLongitude = start.longitude

    val endLatitude = end.latitude
    val endLongitude = end.longitude

    val results = FloatArray(4)

    Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)

    return results[0]
}

fun FragmentManager.startMap(@IdRes mapId: Int,
                             onMapReadyCallback: OnMapReadyCallback) {
    val mapFragment = findFragmentById(mapId) as SupportMapFragment
    mapFragment.getMapAsync(onMapReadyCallback)
}

private fun AppCompatActivity.zoomToDeviceLocation(map: GoogleMap) {
    getCurrentLocation(OnCompleteListener {
        if (it.isSuccessful) {
            val location = it.result
            val latLng = LatLng(location.latitude, location.longitude)
            map.zoomToLocation(latLng)
        }
    })
}
