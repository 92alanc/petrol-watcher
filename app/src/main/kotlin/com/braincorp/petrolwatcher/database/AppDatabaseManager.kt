package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionsReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.math.BigDecimal

/**
 * The database manager used in the app
 */
class AppDatabaseManager : DatabaseManager {

    private companion object {
        const val REFERENCE_VEHICLES = "vehicles"
        const val REFERENCE_PETROL_STATIONS = "petrol_stations"
        const val REFERENCE_AVERAGE_PRICES = "average_prices"
        const val REFERENCE_PREDICTIONS = "predictions"
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

    /**
     * Gets the average price for a fuel
     *
     * @param city the city
     * @param country the country
     * @param fuelType the fuel type
     * @param fuelQuality the fuel quality
     * @param onAveragePriceFoundListener the average price listener
     */
    override fun getAveragePriceForFuel(city: String,
                                        country: String,
                                        fuelType: Fuel.Type,
                                        fuelQuality: Fuel.Quality,
                                        onAveragePriceFoundListener: OnAveragePriceFoundListener) {
        fetchPetrolStations(object: OnPetrolStationsFoundListener {
            override fun onPetrolStationsFound(petrolStations: ArrayList<PetrolStation>) {
                val stationsInTheArea = petrolStations.filter {
                    it.city == city && it.country == country
                }

                // Sum all the fuel prices matching the defined criteria (same price and same quality)
                val sum = stationsInTheArea.sumByDouble {
                    it.fuels.first {
                        fuel -> fuel.type == fuelType && fuel.quality == fuelQuality
                    }.price.toDouble()
                }

                val averageDouble = sum / stationsInTheArea.size
                val averagePrice = AveragePrice(BigDecimal(averageDouble),
                        city,
                        country,
                        fuelType,
                        fuelQuality)
                onAveragePriceFoundListener.onAveragePriceFound(averagePrice)
            }
        })
    }

    /**
     * Gets the average prices for a city and country
     *
     * @param city the city
     * @param country the country
     * @param onAveragePriceFoundListener the average price listener
     */
    override fun getAveragePricesForCityAndCountry(city: String,
                                                   country: String,
                                                   onAveragePriceFoundListener: OnAveragePriceFoundListener) {
        fetchPetrolStations(object: OnPetrolStationsFoundListener {
            override fun onPetrolStationsFound(petrolStations: ArrayList<PetrolStation>) {
                val stationsInTheArea = petrolStations.filter {
                    it.city == city && it.country == country
                }

                val regularPetrolSum = stationsInTheArea.sumByDouble {
                    it.fuels.first {
                        fuel -> fuel.type == Fuel.Type.PETROL && fuel.quality == Fuel.Quality.REGULAR
                    }.price.toDouble()
                }
                val averageRegularPetrolDouble = regularPetrolSum / stationsInTheArea.size
                val regularPetrol = AveragePrice(BigDecimal(averageRegularPetrolDouble),
                        city,
                        country,
                        Fuel.Type.PETROL,
                        Fuel.Quality.REGULAR)

                val premiumPetrolSum = stationsInTheArea.sumByDouble {
                    it.fuels.first {
                        fuel -> fuel.type == Fuel.Type.PETROL && fuel.quality == Fuel.Quality.PREMIUM
                    }.price.toDouble()
                }
                val averagePremiumPetrolDouble = premiumPetrolSum / stationsInTheArea.size
                val premiumPetrol = AveragePrice(BigDecimal(averagePremiumPetrolDouble),
                        city,
                        country,
                        Fuel.Type.PETROL,
                        Fuel.Quality.PREMIUM)

                val ethanolSum = stationsInTheArea.sumByDouble {
                    it.fuels.first {
                        fuel -> fuel.type == Fuel.Type.ETHANOL && fuel.quality == Fuel.Quality.REGULAR
                    }.price.toDouble()
                }
                val averageEthanolDouble = ethanolSum / stationsInTheArea.size
                val ethanol = AveragePrice(BigDecimal(averageEthanolDouble),
                        city,
                        country,
                        Fuel.Type.ETHANOL,
                        Fuel.Quality.REGULAR)

                val dieselSum = stationsInTheArea.sumByDouble {
                    it.fuels.first {
                        fuel -> fuel.type == Fuel.Type.DIESEL && fuel.quality == Fuel.Quality.REGULAR
                    }.price.toDouble()
                }
                val averageDieselDouble = dieselSum / stationsInTheArea.size
                val diesel = AveragePrice(BigDecimal(averageDieselDouble),
                        city,
                        country,
                        Fuel.Type.DIESEL,
                        Fuel.Quality.REGULAR)

                val averagePrices = arrayListOf(regularPetrol,
                        premiumPetrol,
                        ethanol,
                        diesel)

                onAveragePriceFoundListener.onAveragePricesFound(averagePrices)
            }
        })
    }

    /**
     * Saves the average prices for the current location
     *
     * @param averagePrices the average prices
     */
    override fun saveAveragePrices(averagePrices: ArrayList<AveragePrice>) {
        val reference = FirebaseDatabase.getInstance().getReference(REFERENCE_AVERAGE_PRICES)
        reference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                averagePrices.forEach { averagePrice ->
                    val onCompleteListener = OnCompleteListener<Void> { }
                    if (snapshot.child(averagePrice.id).exists())
                        update(averagePrice, reference, onCompleteListener)
                    else
                        insert(averagePrice, reference, onCompleteListener)
                }
            }
        })
    }

    /**
     * Fetches predictions from the database
     *
     * @param onPredictionsReadyListener the callback for new predictions
     */
    override fun fetchPredictions(onPredictionsReadyListener: OnPredictionsReadyListener) {
        FirebaseDatabase.getInstance()
                .getReference(REFERENCE_PREDICTIONS)
                .addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError) { }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val predictions = ArrayList<Prediction>()

                        snapshot.children.forEach {
                            predictions.add(Prediction(it))
                        }

                        onPredictionsReadyListener.onPredictionsReady(predictions)
                    }
                })
    }

    private fun insert(item: Mappable,
                       reference: DatabaseReference,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(item.id)
                .setValue(item.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

    private fun update(item: Mappable,
                       reference: DatabaseReference,
                       onCompleteListener: OnCompleteListener<Void>) {
        reference.child(item.id)
                .updateChildren(item.toMap())
                .addOnCompleteListener(onCompleteListener)
    }

}