package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.mockito.Mock
import org.mockito.Mockito.mock

/**
 * The database manager used in tests
 */
object MockDatabaseManager : DatabaseManager {

    @Suppress("UNCHECKED_CAST")
    @Mock
    private val voidTask: Task<Void> = mock(Task::class.java) as Task<Void>

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

    /**
     * Saves a vehicle
     *
     * @param vehicle the vehicle to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    override fun saveVehicle(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        onCompleteListener.onComplete(voidTask)
    }

}