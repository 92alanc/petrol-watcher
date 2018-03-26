package com.braincorp.petrolwatcher.feature.petrolstations.listeners

import com.braincorp.petrolwatcher.feature.petrolstations.model.PetrolStation

interface OnStationsFoundListener {

    fun onStationsFound(petrolStations: ArrayList<PetrolStation>)

}