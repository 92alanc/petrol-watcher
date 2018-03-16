package com.braincorp.petrolwatcher.listeners

import com.braincorp.petrolwatcher.model.PetrolStation

interface OnStationsFoundListener {

    fun onStationsFound(petrolStations: ArrayList<PetrolStation>)

}