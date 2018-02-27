package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener

private const val DEFAULT_ZOOM = 18f
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
    val cameraPosition = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM)
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

private fun AppCompatActivity.zoomToDeviceLocation(map: GoogleMap) {
    getCurrentLocation(OnCompleteListener {
        if (it.isSuccessful) {
            val location = it.result
            val latLng = LatLng(location.latitude, location.longitude)
            map.zoomToLocation(latLng)
        }
    })
}
