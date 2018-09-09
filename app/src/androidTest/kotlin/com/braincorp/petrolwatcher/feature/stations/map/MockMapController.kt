package com.braincorp.petrolwatcher.feature.stations.map

import android.content.Context
import android.location.Location
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.google.android.gms.maps.OnMapReadyCallback
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

}