package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * The database manager used in the app
 */
class AppDatabaseManager : DatabaseManager, ValueEventListener {

    private lateinit var onVehiclesFoundListener: OnVehiclesFoundListener

    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    override fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener) {
        this.onVehiclesFoundListener = onVehiclesFoundListener
        val reference = FirebaseDatabase.getInstance().getReference("vehicles")
        reference.addValueEventListener(this)
    }

    override fun onCancelled(error: DatabaseError) { }

    override fun onDataChange(snapshot: DataSnapshot) {
        val vehicles = ArrayList<Vehicle>()

        snapshot.children.toList().forEach {
            vehicles.add(Vehicle(it))
        }

        onVehiclesFoundListener.onVehiclesFound(vehicles)
    }

}