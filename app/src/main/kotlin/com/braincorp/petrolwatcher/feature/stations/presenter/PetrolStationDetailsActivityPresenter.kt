package com.braincorp.petrolwatcher.feature.stations.presenter

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.stations.contract.PetrolStationDetailsActivityContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.tasks.OnCompleteListener

/**
 * The implementation of the presentation layer
 * of the petrol station details activity
 */
class PetrolStationDetailsActivityPresenter(private val view: PetrolStationDetailsActivityContract.View)
    : PetrolStationDetailsActivityContract.Presenter {

    /**
     * Saves a petrol station
     *
     * @param petrolStation the petrol station
     */
    override fun savePetrolStation(petrolStation: PetrolStation) {
        if (petrolStation.isValid()) {
            DependencyInjection.databaseManager.savePetrolStation(petrolStation, OnCompleteListener {
                view.showMap()
            })
        } else {
            view.showInvalidPetrolStationDialogue()
        }
    }

    /**
     * Deletes a petrol station
     *
     * @param petrolStation the petrol station
     */
    override fun deletePetrolStation(petrolStation: PetrolStation) {
        DependencyInjection.databaseManager.deletePetrolStation(petrolStation)
        view.showMap()
    }

}