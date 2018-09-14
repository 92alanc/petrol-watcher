package com.braincorp.petrolwatcher.feature.stations.listeners

import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation

/**
 * A listener triggered when a list of
 * vehicles is found
 */
interface OnPetrolStationsFoundListener {

    /**
     * Event triggered when a list of
     * petrol stations is found
     *
     * @param petrolStations the petrol stations found
     */
    fun onPetrolStationsFound(petrolStations: ArrayList<PetrolStation>)

}