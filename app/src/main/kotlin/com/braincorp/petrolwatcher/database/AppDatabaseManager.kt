package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * The database manager used in the app
 */
class AppDatabaseManager : DatabaseManager, ValueEventListener {

    private companion object {
        const val REFERENCE_VEHICLES = "vehicles"
    }

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
        FirebaseDatabase.getInstance().getReference(REFERENCE_VEHICLES).child(
                FirebaseAuth.getInstance().currentUser!!.uid).addValueEventListener(this)
    }

    /**
     * Deletes a vehicle
     *
     * @param vehicle the vehicle to delete
     */
    override fun deleteVehicle(vehicle: Vehicle) {
        val childToDelete = FirebaseDatabase.getInstance().getReference(REFERENCE_VEHICLES).child(
                FirebaseAuth.getInstance().currentUser!!.uid).child(vehicle.id)
        childToDelete.removeValue()
    }

    /**
     * Saves a vehicle
     *
     * @param vehicle the vehicle to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    override fun saveVehicle(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val reference = FirebaseDatabase.getInstance().getReference(REFERENCE_VEHICLES).child(uid)

        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(vehicle.id).exists())
                    update(vehicle, reference, onCompleteListener)
                else
                    insert(vehicle, reference, onCompleteListener)
            }
        })
    }

    override fun onCancelled(error: DatabaseError) { }

    override fun onDataChange(snapshot: DataSnapshot) {
        val vehicles = ArrayList<Vehicle>()

        snapshot.children.toList().forEach {
            vehicles.add(Vehicle(it))
        }

        onVehiclesFoundListener.onVehiclesFound(vehicles)
    }

    private fun <T: Mappable> insert(item: T,
                                     reference: DatabaseReference,
                                     onCompleteListener: OnCompleteListener<Void>) {
        reference.child(item.id)
                .setValue(item.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

    private fun <T: Mappable> update(item: T,
                                     reference: DatabaseReference,
                                     onCompleteListener: OnCompleteListener<Void>) {
        reference.child(item.id)
                .updateChildren(item.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

}