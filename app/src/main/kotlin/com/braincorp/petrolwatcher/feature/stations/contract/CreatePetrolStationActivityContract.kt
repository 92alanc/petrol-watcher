package com.braincorp.petrolwatcher.feature.stations.contract

import android.content.Context
import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.map.OnCurrentLocationFoundListener
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

        /**
         * Gets the current location
         *
         * @param context the Android context
         * @param onCurrentLocationFoundListener the callback to be triggered
         *                                       when the current location is found
         */
        fun getCurrentLocation(context: Context,
                               onCurrentLocationFoundListener: OnCurrentLocationFoundListener)
    }
}