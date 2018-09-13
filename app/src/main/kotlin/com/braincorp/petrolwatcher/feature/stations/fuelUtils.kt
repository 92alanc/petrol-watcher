package com.braincorp.petrolwatcher.feature.stations

import com.braincorp.petrolwatcher.feature.stations.model.Fuel

/**
 * Checks if the new fuel is already present in the set.
 * If positive, the price in the set will be updated,
 * otherwise the new fuel will be added to the set
 *
 * @param fuelSet the set
 * @param newFuel the new fuel
 */
fun updateFuelSet(fuelSet: MutableSet<Fuel>, newFuel: Fuel) {
    val isDuplicate = fuelSet.any {
        newFuel.type == it.type && newFuel.quality == it.quality
    }

    if (isDuplicate) {
        fuelSet.first {
            newFuel.type == it.type && newFuel.quality == it.quality
        }.price = newFuel.price
    } else {
        fuelSet.add(newFuel)
    }
}