package com.braincorp.petrolwatcher.model

import android.location.Address

data class PetrolStation(var name: String,
                         var phoneNumber: String,
                         var address: Address,
                         val prices: Map<Fuel, Float>,
                         var rating: Rating)