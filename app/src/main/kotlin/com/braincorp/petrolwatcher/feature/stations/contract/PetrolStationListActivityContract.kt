package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.maps.model.LatLng

interface PetrolStationListActivityContract {
    /**
     * The view layer of the petrol station list activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Updates the petrol station list
         */
        fun updateList(petrolStations: ArrayList<PetrolStation>)
    }

    /**
     * The presentation layer of the petrol station list activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Fetches all petrol stations within a 5km radius
         *
         * @param hasLocationPermission whether the user has granted the location system
         *                              permission
         * @param currentLocation the current location
         */
        fun fetchPetrolStations(hasLocationPermission: Boolean, currentLocation: LatLng?)
    }
}