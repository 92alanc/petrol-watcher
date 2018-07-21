package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener

/**
 * A database manager
 */
interface DatabaseManager {
    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener)
}