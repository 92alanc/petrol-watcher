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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        reference.child(vehicle.id).setValue(vehicle.toMap())
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
        reference.child(petrolStation.id).setValue(petrolStation.toMap())
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

                val averagePrice = getAveragePrice(city, country, stationsInTheArea,
                                                   fuelType, fuelQuality)
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

                var noAveragePricesFound: Boolean
                val result = ArrayList<AveragePrice>()

                fetchAveragePrices(city, country, object: OnAveragePriceFoundListener {
                    override fun onAveragePriceFound(averagePrice: AveragePrice) {
                        // Not necessary
                    }

                    override fun onAveragePricesFound(averagePrices: ArrayList<AveragePrice>) {
                        noAveragePricesFound = averagePrices.isEmpty()

                        if (!noAveragePricesFound) {
                            averagePrices.forEach {
                                when (it.fuelType) {
                                    Fuel.Type.DIESEL -> {
                                        val diesel = getAveragePrice(city, country, stationsInTheArea,
                                                                     Fuel.Type.DIESEL,
                                                                     Fuel.Quality.REGULAR).apply {
                                            id = it.id
                                        }
                                        saveAveragePrice(diesel)
                                        result.add(diesel)
                                    }

                                    Fuel.Type.ETHANOL -> {
                                        val ethanol = getAveragePrice(city, country, stationsInTheArea,
                                                                      Fuel.Type.ETHANOL,
                                                                      Fuel.Quality.REGULAR).apply {
                                            id = it.id
                                        }
                                        saveAveragePrice(ethanol)
                                        result.add(ethanol)
                                    }

                                    Fuel.Type.PETROL -> {
                                        if (it.fuelQuality == Fuel.Quality.REGULAR) {
                                            val regularPetrol = getAveragePrice(city, country, stationsInTheArea,
                                                                                Fuel.Type.PETROL,
                                                                                Fuel.Quality.REGULAR).apply {
                                                id = it.id
                                            }
                                            saveAveragePrice(regularPetrol)
                                            result.add(regularPetrol)
                                        } else {
                                            val premiumPetrol = getAveragePrice(city, country, stationsInTheArea,
                                                                                Fuel.Type.PETROL,
                                                                                Fuel.Quality.PREMIUM).apply {
                                                id = it.id
                                            }
                                            saveAveragePrice(premiumPetrol)
                                            result.add(premiumPetrol)
                                        }
                                    }
                                }
                            }
                        } else {
                            val diesel = getAveragePrice(city, country, stationsInTheArea,
                                                         Fuel.Type.DIESEL,
                                                         Fuel.Quality.REGULAR)
                            saveAveragePrice(diesel)
                            result.add(diesel)

                            val ethanol = getAveragePrice(city, country, stationsInTheArea,
                                                          Fuel.Type.ETHANOL,
                                                          Fuel.Quality.REGULAR)
                            saveAveragePrice(ethanol)
                            result.add(ethanol)

                            val regularPetrol = getAveragePrice(city, country, stationsInTheArea,
                                                                Fuel.Type.PETROL,
                                                                Fuel.Quality.REGULAR)
                            saveAveragePrice(regularPetrol)
                            result.add(regularPetrol)

                            val premiumPetrol = getAveragePrice(city, country, stationsInTheArea,
                                                                Fuel.Type.PETROL,
                                                                Fuel.Quality.PREMIUM)
                            saveAveragePrice(premiumPetrol)
                            result.add(premiumPetrol)
                        }

                        onAveragePriceFoundListener.onAveragePricesFound(result)
                    }
                })
            }
        })
    }

    /**
     * Saves an average price
     *
     * @param averagePrice the average price
     */
    override fun saveAveragePrice(averagePrice: AveragePrice) {
        val reference = FirebaseDatabase.getInstance().getReference(REFERENCE_AVERAGE_PRICES)
        reference.child(averagePrice.id).setValue(averagePrice.toMap())
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

    private fun getAveragePrice(city: String,
                                country: String,
                                stationsInTheArea: List<PetrolStation>,
                                fuelType: Fuel.Type,
                                fuelQuality: Fuel.Quality): AveragePrice {
        val sum = stationsInTheArea.sumByDouble {
            it.fuels.first {
                fuel -> fuel.type == fuelType && fuel.quality == fuelQuality
            }.price.toDouble()
        }
        val price = BigDecimal(sum / stationsInTheArea.size)

        return AveragePrice(price, city, country, fuelType, fuelQuality)
    }

    private fun fetchAveragePrices(city: String,
                                   country: String,
                                   onAveragePriceFoundListener: OnAveragePriceFoundListener) {
        val reference = FirebaseDatabase.getInstance().getReference(REFERENCE_AVERAGE_PRICES)
        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                val averagePrices = ArrayList<AveragePrice>()

                snapshot.children.forEach { child ->
                    val averagePrice = AveragePrice(child)
                    averagePrice.let {
                        if (it.city == city && it.country == country)
                            averagePrices.add(it)
                    }
                }

                onAveragePriceFoundListener.onAveragePricesFound(averagePrices)
            }
        })
    }

}