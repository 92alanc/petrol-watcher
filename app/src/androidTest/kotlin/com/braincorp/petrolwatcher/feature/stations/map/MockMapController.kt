package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.content.Intent
import android.location.Location
import android.support.annotation.IdRes
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.v4.app.FragmentManager
import com.braincorp.petrolwatcher.base.TestActivity
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.mockito.Mockito.mock

object MockMapController : MapController {

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
                          onMapReadyCallback: OnMapReadyCallback) { }

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCompleteListener the callback to be triggered
     *                           once the location is ready
     */
    override fun getCurrentLocation(context: Context,
                                    onCompleteListener: OnCompleteListener<Location>) {
        @Suppress("UNCHECKED_CAST")
        val mockTask = mock(Task::class.java) as Task<Location>
        onCompleteListener.onComplete(mockTask)
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
    }

    /**
     * Gets an intent for directions to a given address
     *
     * @param destination the destination address
     *
     * @return the intent with the directions
     */
    override fun getDirectionsIntent(destination: String): Intent {
        return Intent(getTargetContext(), TestActivity::class.java)
    }

    /**
     * Zooms the map to the device's current location
     *
     * @param map the map
     * @param context the Android context
     * @param onCurrentLocationFoundListener the callback to be triggered when
     *                                       the current location is found
     */
    override fun zoomToDeviceLocation(map: GoogleMap,
                                      context: Context,
                                      onCurrentLocationFoundListener: OnCurrentLocationFoundListener) {
    }

    /**
     * Gets the distance between 2 points, in metres
     *
     * @param a the start point
     * @param b the end point
     *
     * @return the distance in metres
     */
    override fun getDistanceInMetres(a: LatLng, b: LatLng): Float = 800f

}