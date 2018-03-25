package com.braincorp.petrolwatcher.feature.vehicles.api

import com.braincorp.petrolwatcher.feature.vehicles.api.model.Make
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Model
import com.braincorp.petrolwatcher.feature.vehicles.api.model.ModelDetails
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Years
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface VehicleApi {

    companion object {
        private const val BASE_URL = "https://www.carqueryapi.com/api/0.3/"

        fun getApi(): VehicleApi {
            val gson = getGson()
            val client = getClient()

            val retrofit = Retrofit.Builder()
                    .baseUrl(VehicleApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()

            return retrofit.create(VehicleApi::class.java)
        }

        private fun getGson(): Gson {
            return GsonBuilder().setLenient()
                    .create()
        }

        /**
         * You don't need (and probably don't want) to understand why I put all this mess here.
         * Just accept it
         */
        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor({
                                        val response = it.proceed(it.request())
                                        var responseStr = response.body()?.string()
                                        responseStr = responseStr?.substring(2, responseStr.length)
                                                ?.substring(0, responseStr.length - 4)
                                        val mediaType = response.body()?.contentType()
                                        response.newBuilder()
                                                .body(ResponseBody.create(mediaType, responseStr!!)).build()
                                    }).build()
        }
    }

    @GET("?callback=?&cmd=getYears")
    fun getYears(): Call<Years>

    @GET("?callback=?&cmd=getMakes&year={year}")
    fun getMakes(@Path("year") year: Int): Call<List<Make>>

    @GET("?callback=?&cmd=getModels&year={year}&make={make}")
    fun getModels(@Path("year") year: Int,
                  @Path("make") makeId: String): Call<List<Model>>

    @GET("?callback=?cmd=getTrims&year={year}&make={make}&model={model}")
    fun getDetails(@Path("year") year: Int,
                   @Path("make") makeId: String,
                   @Path("model") modelName: String): Call<List<ModelDetails>>

}