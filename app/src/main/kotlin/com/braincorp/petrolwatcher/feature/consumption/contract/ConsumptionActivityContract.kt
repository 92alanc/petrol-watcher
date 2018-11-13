package com.braincorp.petrolwatcher.feature.consumption.contract

import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.consumption.model.TankLevel

interface ConsumptionActivityContract {
    /**
     * The view layer of the consumption activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows an error in the odometer (start) field
         */
        fun showOdometerStartError()

        /**
         * Shows an error in the odometer (end) field
         */
        fun showOdometerEndError()

        /**
         * Shows a fuel capacity error
         */
        fun showFuelCapacityError()

        /**
         * Shows an invalid distance error
         */
        fun showInvalidDistanceError()

        /**
         * Show an invalid tank level error
         */
        fun showInvalidTankLevelError()

        /**
         * Exports the calculated average consumption
         *
         * @param consumption the calculated average consumption
         */
        fun exportConsumption(consumption: Float)
    }

    /**
     * The presentation layer of the consumption activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Calculates the average consumption
         *
         * @param odometerStart the initial state of the odometer
         * @param odometerEnd the final state of the odometer
         * @param tankLevelStart the initial level of the tank
         * @param tankLevelEnd the final level of the tank
         * @param fuelCapacity the fuel capacity
         */
        fun calculateConsumption(odometerStart: String,
                                 odometerEnd: String,
                                 tankLevelStart: TankLevel,
                                 tankLevelEnd: TankLevel,
                                 fuelCapacity: Int)
    }
}