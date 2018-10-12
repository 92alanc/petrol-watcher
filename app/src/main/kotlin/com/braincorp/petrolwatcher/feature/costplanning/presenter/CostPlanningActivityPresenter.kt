package com.braincorp.petrolwatcher.feature.costplanning.presenter

import android.content.Context
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import com.braincorp.petrolwatcher.feature.costplanning.contract.CostPlanningActivityContract
import com.braincorp.petrolwatcher.feature.stations.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.location.places.Place
import java.util.*

/**
 * The implementation of the cost planning
 * activity presenter
 */
class CostPlanningActivityPresenter(private val view: CostPlanningActivityContract.View)
    : CostPlanningActivityContract.Presenter {

    /**
     * Fetches all vehicles belonging to the current user
     */
    override fun fetchVehicles() {
        DependencyInjection.databaseManager.fetchVehicles(object : OnVehiclesFoundListener {
            /**
             * Event triggered when a list of
             * vehicles is found
             *
             * @param vehicles the vehicles found
             */
            override fun onVehiclesFound(vehicles: ArrayList<Vehicle>) {
                view.updateVehicles(vehicles)
            }
        })
    }

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCurrentLocationFoundListener the callback to be triggered
     *                                       when the current location is
     *                                       found
     */
    override fun getCurrentLocation(context: Context,
                                    onCurrentLocationFoundListener: OnCurrentLocationFoundListener) {
        DependencyInjection.mapController.getCurrentLocation(context, onCurrentLocationFoundListener)
    }

    /**
     * Estimates the cost and the fuel amount
     * necessary for a trip
     *
     * @param origin the origin
     * @param destination the destination
     * @param vehicle the vehicle
     * @param tankState the tank state
     */
    override fun estimateCostAndFuelAmount(origin: Place,
                                           destination: Place,
                                           vehicle: Vehicle,
                                           tankState: TankState) {
        // TODO
    }

}