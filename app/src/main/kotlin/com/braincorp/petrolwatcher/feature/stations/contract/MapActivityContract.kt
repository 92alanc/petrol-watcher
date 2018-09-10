package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener

interface MapActivityContract {
    /**
     * The view layer of the map activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Starts the main activity
         */
        fun startMainActivity()
    }

    /**
     * The presentation layer of the map activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Ends the current session
         */
        fun signOut()

        /**
         * Fetches all petrol stations
         *
         * @param onPetrolStationsFoundListener the callback to be triggered
         *                                      when the petrol stations are
         *                                      found
         */
        fun fetchPetrolStations(onPetrolStationsFoundListener: OnPetrolStationsFoundListener)
    }
}