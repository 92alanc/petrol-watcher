package com.braincorp.petrolwatcher.feature.vehicles.presenter

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.tasks.OnCompleteListener

/**
 * The presentation layer of the vehicle details activity
 */
class VehicleDetailsActivityPresenter(private val view: VehicleDetailsActivityContract.View)
    : VehicleDetailsActivityContract.Presenter {

    /**
     * Saves a vehicle
     *
     * @param vehicle the vehicle to save
     */
    override fun saveVehicle(vehicle: Vehicle) {
        if (vehicle.isValid()) {
            DependencyInjection.databaseManager.saveVehicle(vehicle, OnCompleteListener {
                view.showVehicleList()
            })
        } else {
            view.showInvalidVehicleDialogue()
        }
    }

    /**
     * Deletes a vehicle
     *
     * @param vehicle the vehicle to delete
     */
    override fun deleteVehicle(vehicle: Vehicle) {
        DependencyInjection.databaseManager.deleteVehicle(vehicle)
        view.showVehicleList()
    }

}