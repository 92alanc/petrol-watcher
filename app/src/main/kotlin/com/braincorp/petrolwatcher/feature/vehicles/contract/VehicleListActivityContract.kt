package com.braincorp.petrolwatcher.feature.vehicles.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

interface VehicleListActivityContract {
    /**
     * The view layer of the vehicle list activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Updates the list
         *
         * @param vehicles the vehicles
         */
        fun updateList(vehicles: ArrayList<Vehicle>)
    }

    /**
     * The presentation layer of the vehicle list activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Fetches all vehicles belonging to the
         * currently signed in user
         */
        fun fetchVehicles()
    }
}