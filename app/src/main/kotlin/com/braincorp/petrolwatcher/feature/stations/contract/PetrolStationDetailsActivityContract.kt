package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation

interface PetrolStationDetailsActivityContract {
    /**
     * The view layer of the petrol station details
     * activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows an invalid petrol station dialogue
         */
        fun showInvalidPetrolStationDialogue()

        /**
         * Shows the map activity
         */
        fun showMap()
    }

    /**
     * The presentation layer of the petrol station
     * details activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Saves a petrol station
         *
         * @param petrolStation the petrol station
         */
        fun savePetrolStation(petrolStation: PetrolStation)
    }
}