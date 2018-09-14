package com.braincorp.petrolwatcher.feature.stations.presenter

import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.feature.stations.contract.MapActivityContract
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener

/**
 * The implementation of the presentation layer
 * of the map activity
 */
class MapActivityPresenter(private val view: MapActivityContract.View)
    : MapActivityContract.Presenter {

    /**
     * Ends the current session
     */
    override fun signOut() {
        DependencyInjection.authenticator.signOut()
        view.startMainActivity()
    }

    /**
     * Fetches all petrol stations
     *
     * @param onPetrolStationsFoundListener the callback to be triggered
     *                                      when the petrol stations are
     *                                      found
     */
    override fun fetchPetrolStations(onPetrolStationsFoundListener: OnPetrolStationsFoundListener) {
        DependencyInjection.databaseManager.fetchPetrolStations(onPetrolStationsFoundListener)
    }

}