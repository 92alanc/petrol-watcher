package com.braincorp.petrolwatcher.feature.stations.presenter

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.stations.contract.PetrolStationListActivityContract
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation

/**
 * The implementation of the presentation layer of
 * the petrol station list activity
 */
class PetrolStationListActivityPresenter(private val view: PetrolStationListActivityContract.View)
    : PetrolStationListActivityContract.Presenter,
        OnPetrolStationsFoundListener {

    /**
     * Fetches all petrol stations within a radius of 5km
     */
    override fun fetchPetrolStations() {
        DependencyInjection.databaseManager.fetchPetrolStations(onPetrolStationsFoundListener = this)
    }

    /**
     * Event triggered when a list of
     * petrol stations is found
     *
     * @param petrolStations the petrol stations found
     */
    override fun onPetrolStationsFound(petrolStations: ArrayList<PetrolStation>) {
        view.updateList(petrolStations)
    }

}