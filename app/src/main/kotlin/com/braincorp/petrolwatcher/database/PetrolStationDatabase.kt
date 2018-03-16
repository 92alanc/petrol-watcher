package com.braincorp.petrolwatcher.database

import android.content.Context
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.services.StationLocatorService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object PetrolStationDatabase {

    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("petrol_stations")

    fun insertOrUpdate(petrolStation: PetrolStation,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError?) { }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot?.child(petrolStation.id)?.exists()!!)
                    update(petrolStation, onCompleteListener)
                else
                    insert(petrolStation, onCompleteListener)
            }
        })
    }

    fun delete(petrolStation: PetrolStation,
               onCompleteListener: OnCompleteListener<Void>) {
        reference.child(petrolStation.id)
                .removeValue()
                .addOnCompleteListener(onCompleteListener)
    }

    fun select(context: Context,
               filterByLocation: Boolean,
               valueEventListener: ValueEventListener) {
        if (filterByLocation) {
            context.startService(StationLocatorService.getIntent(context,
                    radius = StationLocatorService.MAX_RADIUS))
        } else {
            reference.addValueEventListener(valueEventListener)
        }
    }

    private fun insert(petrolStation: PetrolStation,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(petrolStation.id)
                .setValue(petrolStation.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

    private fun update(petrolStation: PetrolStation,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(petrolStation.id)
                .updateChildren(petrolStation.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

}