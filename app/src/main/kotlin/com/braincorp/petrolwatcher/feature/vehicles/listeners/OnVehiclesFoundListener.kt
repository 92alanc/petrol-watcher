package com.braincorp.petrolwatcher.feature.vehicles.listeners

import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

/**
 * A listener triggered when a list of
 * vehicles is found
 */
interface OnVehiclesFoundListener {
    /**
     * Event triggered when a list of
     * vehicles is found
     *
     * @param vehicles the vehicles found
     */
    fun onVehiclesFound(vehicles: ArrayList<Vehicle>)
}