package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation

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
         * Fetches all petrol stations within a radius of 5km
         */
        fun fetchPetrolStations()
    }
}