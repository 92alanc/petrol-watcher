package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.model.Vehicle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object VehicleDatabase {

    private val database = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference

    init {
        database.setPersistenceEnabled(true)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        reference = database.getReference("vehicles").child(uid)
    }

    fun insertOrUpdate(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError?) { }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot?.child(vehicle.id)?.exists()!!)
                    update(vehicle, onCompleteListener)
                else
                    insert(vehicle, onCompleteListener)
            }
        })
    }

    fun delete(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        reference.child(vehicle.id)
                .removeValue()
                .addOnCompleteListener(onCompleteListener)
    }

    fun select(valueEventListener: ValueEventListener) {
        reference.addValueEventListener(valueEventListener)
    }

    private fun insert(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        reference.child(vehicle.id)
                .setValue(vehicle.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

    private fun update(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        reference.child(vehicle.id)
                .updateChildren(vehicle.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

}