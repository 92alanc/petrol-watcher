package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation

interface CreatePetrolStationActivityContract {
    /**
     * The view layer of the create petrol station activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows the petrol station list
         */
        fun showPetrolStationList()

        /**
         * Shows an invalid petrol station dialogue
         */
        fun showInvalidPetrolStationDialogue()
    }

    /**
     * The presentation layer of the create petrol station activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Saves a petrol station
         */
        fun savePetrolStation(petrolStation: PetrolStation)
    }
}