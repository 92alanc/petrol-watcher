package com.braincorp.petrolwatcher.feature.vehicles.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

interface CreateVehicleActivityContract {
    /**
     * The view layer of the create vehicle activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Sets the year range
         *
         * @param range the range
         */
        fun setYearRange(range: IntRange)

        /**
         * Sets the manufacturer list
         *
         * @param manufacturers the list
         */
        fun setManufacturerList(manufacturers: ArrayList<String>)

        /**
         * Sets the models list
         *
         * @param models the list
         */
        fun setModelsList(models: ArrayList<String>)

        /**
         * Sets the vehicle details list
         *
         * @param detailsList the list
         */
        fun setDetailsList(detailsList: ArrayList<Vehicle.Details>)

        /**
         * Shows the vehicle list
         */
        fun showVehicleList()

        /**
         * Shows an invalid vehicle dialogue
         */
        fun showInvalidVehicleDialogue()
    }

    /**
     * The presentation layer of the create vehicle
     * activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Gets the year range available
         * in the API
         */
        fun getYearRange()

        /**
         * Gets a list of manufacturers from the API
         * based on a year
         *
         * @param year the year
         */
        fun getManufacturers(year: Int)

        /**
         * Gets a list of models from the API
         * based on a year and a manufacturer
         *
         * @param year the year
         * @param manufacturer the manufacturer
         */
        fun getModels(year: Int, manufacturer: String)

        /**
         * Gets a model's details from the API
         *
         * @param year the year
         * @param manufacturer the manufacturer
         * @param model the model
         */
        fun getDetails(year: Int, manufacturer: String, model: String)

        /**
         * Saves a vehicle
         *
         * @param vehicle the vehicle to save
         */
        fun saveVehicle(vehicle: Vehicle)
    }
}