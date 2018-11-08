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

/**
 * A database manager
 */
interface DatabaseManager {
    /**
     * Fetches all vehicles belonging to the currently
     * signed in user
     *
     * @param onVehiclesFoundListener the listener to be triggered when the
     *                                query is complete
     */
    fun fetchVehicles(onVehiclesFoundListener: OnVehiclesFoundListener)

    /**
     * Deletes a vehicle
     *
     * @param vehicle the vehicle to delete
     */
    fun deleteVehicle(vehicle: Vehicle)

    /**
     * Saves a vehicle
     *
     * @param vehicle the vehicle to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    fun saveVehicle(vehicle: Vehicle, onCompleteListener: OnCompleteListener<Void>)

    /**
     * Saves a petrol station
     *
     * @param petrolStation the petrol station to save
     * @param onCompleteListener the callback to be triggered when the
     *                           operation is complete
     */
    fun savePetrolStation(petrolStation: PetrolStation, onCompleteListener: OnCompleteListener<Void>)

    /**
     * Deletes a petrol station
     *
     * @param petrolStation the petrol station to delete
     */
    fun deletePetrolStation(petrolStation: PetrolStation)

    /**
     * Fetches all petrol stations
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     */
    fun fetchPetrolStations(onPetrolStationsFoundListener: OnPetrolStationsFoundListener)

    /**
     * Fetches all petrol stations within a 5km radius
     *
     * @param onPetrolStationsFoundListener the listener to be triggered when the
     *                                      query is complete
     * @param hasLocationPermission whether the user has granted the location system
     *                              permission
     * @param currentLocation the current location
     */
    fun fetchPetrolStationsWithin5kmRadius(onPetrolStationsFoundListener: OnPetrolStationsFoundListener,
                                           hasLocationPermission: Boolean,
                                           currentLocation: LatLng?)

    /**
     * Gets the average price for a fuel
     *
     * @param city the city
     * @param country the country
     * @param fuelType the fuel type
     * @param fuelQuality the fuel quality
     * @param onAveragePriceFoundListener the average price listener
     */
    fun getAveragePriceForFuel(city: String,
                               country: String,
                               fuelType: Fuel.Type,
                               fuelQuality: Fuel.Quality,
                               onAveragePriceFoundListener: OnAveragePriceFoundListener)

    /**
     * Gets the average prices for a city and country
     *
     * @param city the city
     * @param country the country
     * @param onAveragePriceFoundListener the average price listener
     */
    fun getAveragePricesForCityAndCountry(city: String,
                                          country: String,
                                          onAveragePriceFoundListener: OnAveragePriceFoundListener)

    /**
     * Saves an average price
     *
     * @param averagePrice the average price
     */
    fun saveAveragePrice(averagePrice: AveragePrice)

    /**
     * Fetches a prediction for an area from the database
     *
     * @param area the area
     * @param onPredictionReadyListener the callback for the new prediction
     */
    fun fetchPrediction(area: String, onPredictionReadyListener: OnPredictionReadyListener)
}