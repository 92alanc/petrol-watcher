package com.braincorp.petrolwatcher.feature.prediction.model

import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.google.firebase.database.DataSnapshot

/**
 * A fuel price prediction
 */
data class Prediction(var id: String = "",
                      var fuelData: Fuel = Fuel(),
                      var city: String = "",
                      var country: String = "") {

    private companion object {
        const val KEY_ID = "id"
        const val KEY_FUEL_DATA = "fuel_data"
        const val KEY_CITY = "city"
        const val KEY_COUNTRY = "country"
    }

    constructor(snapshot: DataSnapshot): this() {
        with(snapshot) {
            id = child(KEY_ID).value.toString()
            fuelData = Fuel(child(KEY_FUEL_DATA))
            city = child(KEY_CITY).value.toString()
            country = child(KEY_COUNTRY).value.toString()
        }
    }

}