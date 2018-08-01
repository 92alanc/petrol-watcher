package com.braincorp.petrolwatcher.feature.vehicles.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface VehicleDetailsActivityContract {
    /**
     * The view layer of the vehicle details activity
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
        fun setManufacturerList(manufacturers: List<String>)

        /**
         * Sets the models list
         *
         * @param models the list
         */
        fun setModelsList(models: List<String>)

        /**
         * Sets the trim level list
         *
         * @param trimLevels the list
         */
        fun setTrimLevelList(trimLevels: List<String>)
    }

    /**
     * The presentation layer of the vehicle details
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
    }
}