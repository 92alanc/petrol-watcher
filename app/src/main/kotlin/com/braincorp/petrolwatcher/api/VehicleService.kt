package com.braincorp.petrolwatcher.api

import com.braincorp.petrolwatcher.api.model.Years
import retrofit2.Call
import retrofit2.http.GET

interface VehicleService {

    companion object {
        const val BASE_URL = "https://www.carqueryapi.com/api/0.3/"
    }

    @GET("?callback=?&cmd=getYears")
    fun getYears(): Call<Years>

}