package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

/**
 * The database manager used in tests
 */
object MockDatabaseManager : DatabaseManager {

    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    override fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener) {
        val vehicleA = Vehicle(manufacturer = "Volkswagen", model = "Golf",
                year = 2013, details = Vehicle.Details(trimLevel = "1.6"))
        val vehicleB = Vehicle(manufacturer = "Audi", model = "Q7", year = 2015,
                details = Vehicle.Details(trimLevel = "2.0"))
        val vehicleC = Vehicle(manufacturer = "BMW", model = "Z3", year = 2011,
                details = Vehicle.Details(trimLevel = "1.8"))

        val vehicles = arrayListOf(vehicleA, vehicleB, vehicleC)
        onVehiclesFoundListener.onVehiclesFound(vehicles)
    }

    /**
     * Deletes a vehicle
     *
     * @param vehicle the vehicle to delete
     */
    override fun deleteVehicle(vehicle: Vehicle) { }

}