package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.prediction.listeners.OnPredictionReadyListener
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.mockito.Mock
import org.mockito.Mockito.mock
import java.math.BigDecimal

/**
 * The database manager used in tests
 */
object MockDatabaseManager : DatabaseManager {

    @Suppress("UNCHECKED_CAST")
    @Mock
    private val voidTask: Task<Void> = mock(Task::class.java) as Task<Void>

    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be
     *                                triggered when the
     *                                query is complete
     */
    override fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener) {
        val vehicleA = Vehicle(manufacturer = "Volkswagen", model = "Golf",
                year = 2013, details = Vehicle.Details(trimLevel = "1.6"))
        val vehicleB = Vehicle(manufacturer = "Audi", model = "Q7", year = 2015,
                details = Vehicle.Details(trimLevel = "2.0"))
        val vehicleC = Vehicle(manufacturer = "BMW", model = "Z3", year = 2011,
                details = Vehicle.Details(trimLevel = "1.8"))

        val vehicles = arrayListOf(vehicleA, vehicleB, vehicleC)
        onVehiclesFoundListener.onVehiclesFound(vehicles)
    }

    /**
     * Deletes a vehicle
     *
     * @param vehicle the vehicle to delete
     */
    override fun deleteVehicle(vehicle: Vehicle) { }

    /**
     * Saves a vehicle
     *
     * @param vehicle the vehicle to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    override fun saveVehicle(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>) {
        onCompleteListener.onComplete(voidTask)
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
        onCompleteListener.onComplete(voidTask)
    }

    /**
     * Deletes a petrol station
     *
     * @param petrolStation the petrol station to delete
     */
    override fun deletePetrolStation(petrolStation: PetrolStation) { }

    /**
     * Fetches all petrol stations
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     */
    override fun fetchPetrolStations(onPetrolStationsFoundListener: OnPetrolStationsFoundListener) {
        val stationA = PetrolStation(name = "BP", rating = 4)
        val stationB = PetrolStation(name = "Shell", rating = 3)
        val stationC = PetrolStation(name = "BR", rating = 1)

        val petrolStations = arrayListOf(stationA, stationB, stationC)
        onPetrolStationsFoundListener.onPetrolStationsFound(petrolStations)
    }

    /**
     * Fetches all petrol stations within a 5km radius
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     * @param hasLocationPermission whether the user has granted the location system
     *                           permission
     * @param currentLocation the current location
     */
    override fun fetchPetrolStationsWithin5kmRadius(onPetrolStationsFoundListener: OnPetrolStationsFoundListener,
                                                    hasLocationPermission: Boolean,
                                                    currentLocation: LatLng?) {
        val stationA = PetrolStation(name = "Shell", rating = 3)
        val stationB = PetrolStation(name = "BR", rating = 1)

        val petrolStations = arrayListOf(stationA, stationB)
        onPetrolStationsFoundListener.onPetrolStationsFound(petrolStations)
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
        val averagePrice = AveragePrice(BigDecimal(4.8),
                city,
                country,
                fuelType,
                fuelQuality)
        onAveragePriceFoundListener.onAveragePriceFound(averagePrice)
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
        val regularPetrol = AveragePrice(BigDecimal(4.8),
                city,
                country,
                Fuel.Type.PETROL,
                Fuel.Quality.REGULAR)
        val premiumPetrol = AveragePrice(BigDecimal(4.8),
                city,
                country,
                Fuel.Type.PETROL,
                Fuel.Quality.PREMIUM)
        val ethanol = AveragePrice(BigDecimal(4.8),
                city,
                country,
                Fuel.Type.ETHANOL,
                Fuel.Quality.REGULAR)
        val diesel = AveragePrice(BigDecimal(4.8),
                city,
                country,
                Fuel.Type.DIESEL,
                Fuel.Quality.REGULAR)

        onAveragePriceFoundListener.onAveragePricesFound(arrayListOf(regularPetrol,
                premiumPetrol,
                ethanol,
                diesel))
    }

    /**
     * Fetches predictions from the database
     *
     * @param onPredictionReadyListener the callback for new predictions
     */
    override fun fetchPrediction(onPredictionReadyListener: OnPredictionReadyListener) {
        // TODO: implement
    }

    /**
     * Saves an average price
     *
     * @param averagePrice the average price
     */
    override fun saveAveragePrice(averagePrice: AveragePrice) { }

}