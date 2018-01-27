package com.braincorp.petrolwatcher.model

import android.location.Address

data class PetrolStation(var name: String,
                         var phoneNumber: String,
                         var address: Address,
                         val prices: Map<Map<FuelType, FuelQuality>, Float>,
                         var rating: Rating) {

    val id = "$name - ${address.latitude}${address.longitude}"

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["id"] = id
        map["name"] = name
        map["phone_number"] = phoneNumber
        map["address"] = address
        map["prices"] = prices
        map["rating"] = rating
        return map
    }

}