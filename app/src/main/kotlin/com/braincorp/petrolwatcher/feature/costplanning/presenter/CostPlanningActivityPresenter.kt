package com.braincorp.petrolwatcher.feature.costplanning.presenter

import android.content.Context
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.database.OnAveragePriceFoundListener
import com.braincorp.petrolwatcher.feature.consumption.model.RoadType
import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import com.braincorp.petrolwatcher.feature.costplanning.contract.CostPlanningActivityContract
import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.utils.tankStateToLitres
import com.google.android.gms.location.places.Place
import java.math.BigDecimal
import kotlin.math.roundToInt

/**
 * The implementation of the cost planning
 * activity presenter
 */
class CostPlanningActivityPresenter(private val view: CostPlanningActivityContract.View)
    : CostPlanningActivityContract.Presenter {

    /**
     * Fetches all vehicles belonging to the current user
     */
    override fun fetchVehicles() {
        DependencyInjection.databaseManager.fetchVehicles(object : OnVehiclesFoundListener {
            /**
             * Event triggered when a list of
             * vehicles is found
             *
             * @param vehicles the vehicles found
             */
            override fun onVehiclesFound(vehicles: ArrayList<Vehicle>) {
                view.updateVehicles(vehicles)
            }
        })
    }

    /**
     * Gets the current location
     *
     * @param context the Android context
     * @param onCurrentLocationFoundListener the callback to be triggered
     *                                       when the current location is
     *                                       found
     */
    override fun getCurrentLocation(context: Context,
                                    onCurrentLocationFoundListener: OnCurrentLocationFoundListener) {
        DependencyInjection.mapController.getCurrentLocation(context, onCurrentLocationFoundListener)
    }

    /**
     * Estimates the cost and the fuel amount
     * necessary for a trip
     *
     * @param context the Android context
     * @param origin the origin
     * @param destination the destination
     * @param fuelType the fuel type
     * @param fuelQuality the fuel quality
     * @param vehicle the vehicle
     * @param tankState the tank state
     */
    override fun estimateCostAndFuelAmount(context: Context,
                                           origin: Place,
                                           destination: Place,
                                           fuelType: Fuel.Type,
                                           fuelQuality: Fuel.Quality,
                                           vehicle: Vehicle,
                                           tankState: TankState,
                                           roadType: RoadType) {
        val mapController = DependencyInjection.mapController
        val city = mapController.getCityFromPlace(context, origin)
        val country = mapController.getCountryFromPlace(context, origin)
        val distanceKm = mapController.getDistanceInMetres(origin.latLng, destination.latLng) / 1000
        DependencyInjection.databaseManager.getAveragePriceForFuel(city, country,
                fuelType, fuelQuality, object: OnAveragePriceFoundListener {
            override fun onAveragePriceFound(averagePrice: AveragePrice) {
                val tankStateLitres = tankStateToLitres(tankState, vehicle.details.fuelCapacity)
                val consumption = when (roadType) {
                    RoadType.MOTORWAY -> {
                        if (vehicle.calculatedValues.avgConsumptionMotorway > 0f)
                            vehicle.calculatedValues.avgConsumptionMotorway
                        else
                            vehicle.details.avgConsumptionMotorway
                    }

                    RoadType.URBAN_ROAD -> {
                        if (vehicle.calculatedValues.avgConsumptionCity > 0f)
                            vehicle.calculatedValues.avgConsumptionCity
                        else
                            vehicle.details.avgConsumptionCity
                    }
                }
                // (distanceKm / consumption) = total fuel amount necessary, disregarding current tank state
                var fuelAmount = ((distanceKm / consumption) - tankStateLitres).roundToInt()
                var cost = BigDecimal(fuelAmount * averagePrice.price.toDouble())

                fuelAmount = if (fuelAmount > 0) fuelAmount
                else 0

                cost = if (cost > BigDecimal.ZERO) cost
                else BigDecimal.ZERO

                view.updateEstimatedCostAndFuelAmount(cost, fuelAmount)
            }

            override fun onAveragePricesFound(averagePrices: ArrayList<AveragePrice>) {
                // Not necessary
            }
        })
    }

}