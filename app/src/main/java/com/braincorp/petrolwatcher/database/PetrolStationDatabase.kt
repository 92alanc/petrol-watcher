package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.model.TestModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*

object PetrolStationDatabase {

    private val database = FirebaseDatabase.getInstance()
    private val reference: DatabaseReference

    init {
        database.setPersistenceEnabled(true)
        database.setLogLevel(Logger.Level.DEBUG)
        reference = database.getReference("petrol_stations")
    }

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

    fun insert(testModel: TestModel, onCompleteListener: OnCompleteListener<Void>) {
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError?) { }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot?.child(testModel.id)?.exists()!!) {
                    reference.child(testModel.id)
                            .updateChildren(testModel.toMap())
                            .addOnCompleteListener(onCompleteListener)
                } else {
                    reference.child(testModel.id)
                            .setValue(testModel)
                            .addOnCompleteListener(onCompleteListener)
                }
            }
        })
    }

    fun select(valueEventListener: ValueEventListener) {
        reference.addValueEventListener(valueEventListener)
    }

    private fun insert(petrolStation: PetrolStation,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(petrolStation.id)
                .setValue(petrolStation)
                .addOnCompleteListener(onCompleteListener)
    }

    private fun update(petrolStation: PetrolStation,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(petrolStation.id)
                .updateChildren(petrolStation.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

}