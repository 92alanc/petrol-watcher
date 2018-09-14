package com.braincorp.petrolwatcher.feature.vehicles.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

interface VehicleDetailsActivityContract {
    /**
     * The view layer of the vehicle details activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows the vehicle list
         */
        fun showVehicleList()

        /**
         * Shows an invalid vehicle dialogue
         */
        fun showInvalidVehicleDialogue()
    }

    interface Presenter : BaseContract.Presenter {
        /**
         * Saves a vehicle
         *
         * @param vehicle the vehicle to save
         */
        fun saveVehicle(vehicle: Vehicle)

        /**
         * Deletes a vehicle
         *
         * @param vehicle the vehicle to delete
         */
        fun deleteVehicle(vehicle: Vehicle)
    }
}