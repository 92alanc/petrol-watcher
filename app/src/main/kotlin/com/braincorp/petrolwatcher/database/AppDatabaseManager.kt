package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener

/**
 * The database manager used in the app
 */
class AppDatabaseManager : DatabaseManager {

    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    override fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener) {

    }

}