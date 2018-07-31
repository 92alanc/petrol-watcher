package com.braincorp.petrolwatcher.feature.vehicles.presenter

import com.braincorp.petrolwatcher.database.DatabaseManager
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleListActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnItemRemovedListener
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnVehiclesFoundListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

/**
 * The implementation of the presentation layer
 * of the vehicle list activity
 */
class VehicleListActivityPresenter(private val view: VehicleListActivityContract.View,
                                   private val databaseManager: DatabaseManager)
    : VehicleListActivityContract.Presenter, OnVehiclesFoundListener,
      OnItemRemovedListener<Vehicle> {

    /**
     * Fetches all vehicles belonging to the
     * currently signed in user
     */
    override fun fetchVehicles() {
        databaseManager.fetchVehicles(onVehiclesFoundListener = this)
    }

    /**
     * Event triggered when a list of
     * vehicles is found
     *
     * @param vehicles the vehicles found
     */
    override fun onVehiclesFound(vehicles: ArrayList<Vehicle>) {
        view.updateList(vehicles)
    }

    /**
     * Function called when an item is removed
     *
     * @param item the item removed
     */
    override fun onItemRemoved(item: Vehicle) {
        databaseManager.deleteVehicle(item)
    }

}