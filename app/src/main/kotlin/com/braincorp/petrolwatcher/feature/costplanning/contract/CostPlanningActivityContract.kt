package com.braincorp.petrolwatcher.feature.costplanning.contract

import android.content.Context
import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import com.braincorp.petrolwatcher.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.location.places.Place
import java.math.BigDecimal

interface CostPlanningActivityContract {
    /**
     * The view layer of the cost planning activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Updates the vehicle spinner
         *
         * @param vehicles the vehicles
         */
        fun updateVehicles(vehicles: ArrayList<Vehicle>)

        /**
         * Updates the estimated cost and fuel amount
         *
         * @param cost the estimated cost
         * @param fuelAmount the estimated fuel amount, in litres
         */
        fun updateEstimatedCostAndFuelAmount(cost: BigDecimal, fuelAmount: Int)
    }

    /**
     * The presentation layer of the cost planning activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Fetches all vehicles belonging to the current user
         */
        fun fetchVehicles()

        /**
         * Gets the current location
         *
         * @param context the Android context
         * @param onCurrentLocationFoundListener the callback to be triggered
         *                                       when the current location is
         *                                       found
         */
        fun getCurrentLocation(context: Context,
                               onCurrentLocationFoundListener: OnCurrentLocationFoundListener)

        /**
         * Estimates the cost and the fuel amount
         * necessary for a trip
         *
         * @param origin the origin
         * @param destination the destination
         * @param vehicle the vehicle
         * @param tankState the tank state
         */
        fun estimateCostAndFuelAmount(origin: Place,
                                      destination: Place,
                                      vehicle: Vehicle,
                                      tankState: TankState)
    }
}