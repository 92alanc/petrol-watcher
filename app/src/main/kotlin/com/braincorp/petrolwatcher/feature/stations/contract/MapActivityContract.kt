package com.braincorp.petrolwatcher.feature.stations.contract

import com.braincorp.petrolwatcher.base.BaseContract

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
    }
}