package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * The database manager used in the app
 */
class AppDatabaseManager : DatabaseManager {

    private companion object {
        const val REFERENCE_VEHICLES = "vehicles"
        const val REFERENCE_PETROL_STATIONS = "petrol_stations"
    }

    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    override fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference(REFERENCE_VEHICLES).child(uid)
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) { }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val vehicles = ArrayList<Vehicle>()

                        snapshot.children.toList().forEach {
                            vehicles.add(Vehicle(it))
                        }

                        onVehiclesFoundListener.onVehiclesFound(vehicles)
                    }
                })
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

    /**
     * Saves a petrol station
     *
     * @param petrolStation the petrol station to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    override fun savePetrolStation(petrolStation: PetrolStation,
                                   onCompleteListener: OnCompleteListener<Void>) {
        val reference = FirebaseDatabase.getInstance().getReference(REFERENCE_PETROL_STATIONS)
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(petrolStation.id).exists())
                    update(petrolStation, reference, onCompleteListener)
                else
                    insert(petrolStation, reference, onCompleteListener)
            }
        })
    }

    /**
     * Deletes a petrol station
     *
     * @param petrolStation the petrol station to delete
     */
    override fun deletePetrolStation(petrolStation: PetrolStation) {
        val childToDelete = FirebaseDatabase.getInstance().getReference(REFERENCE_PETROL_STATIONS)
                .child(petrolStation.id)
        childToDelete.removeValue()
    }

    /**
     * Fetches all petrol stations
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     */
    override fun fetchPetrolStations(onPetrolStationsFoundListener: OnPetrolStationsFoundListener) {
        FirebaseDatabase.getInstance().getReference(REFERENCE_PETROL_STATIONS)
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) { }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val petrolStations = ArrayList<PetrolStation>()

                        snapshot.children.toList().forEach {
                            petrolStations.add(PetrolStation(it))
                        }

                        onPetrolStationsFoundListener.onPetrolStationsFound(petrolStations)
                    }
                })
    }

    /**
     * Fetches all petrol stations within a 5km radius
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     * @param hasLocationPermission whether the user has granted the location system
     *                              permission
     * @param currentLocation the current location
     */
    override fun fetchPetrolStationsWithin5kmRadius(onPetrolStationsFoundListener: OnPetrolStationsFoundListener,
                                                    hasLocationPermission: Boolean,
                                                    currentLocation: LatLng?) {
        FirebaseDatabase.getInstance().getReference(REFERENCE_PETROL_STATIONS)
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) { }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val petrolStations = ArrayList<PetrolStation>()

                        snapshot.children.toList().forEach {
                            val petrolStation = PetrolStation(it)

                            if (hasLocationPermission && currentLocation != null) {
                                val mapController = DependencyInjection.mapController
                                val distance = mapController.getDistanceInMetres(currentLocation,
                                        petrolStation.latLng)
                                if (distance <= 5000)
                                    petrolStations.add(petrolStation)
                            } else {
                                petrolStations.add(petrolStation)
                            }
                        }

                        onPetrolStationsFoundListener.onPetrolStationsFound(petrolStations)
                    }
                })
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