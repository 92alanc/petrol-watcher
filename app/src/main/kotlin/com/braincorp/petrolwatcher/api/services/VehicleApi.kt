package com.braincorp.petrolwatcher.api.services

import com.braincorp.petrolwatcher.api.VehicleService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VehicleApi {

    fun getService(): VehicleService {
        val retrofit = Retrofit.Builder()
                .baseUrl(VehicleService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(VehicleService::class.java)
    }

}