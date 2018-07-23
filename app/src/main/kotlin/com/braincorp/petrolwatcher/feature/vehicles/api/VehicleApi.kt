package com.braincorp.petrolwatcher.feature.vehicles.api

import com.braincorp.petrolwatcher.feature.vehicles.api.model.Makes
import com.braincorp.petrolwatcher.feature.vehicles.api.model.ModelDetails
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Models
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Years
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A vehicle API
 */
interface VehicleApi {

    /**
     * Gets a range of years
     *
     * @return a call for the API
     */
    @GET("?callback=?&cmd=getYears")
    fun getYears(): Call<Years>

    /**
     * Gets a list of manufacturers by year
     *
     * @param year the year
     *
     * @return a call for the API
     */
    @GET("?callback=?&cmd=getMakes")
    fun getMakes(@Query("year") year: Int): Call<Makes>

    /**
     * Gets a list of models
     *
     * @param year the year
     * @param makeId the manufacturer ID
     *
     * @return a call for the API
     */
    @GET("?callback=?&cmd=getModels")
    fun getModels(@Query("year") year: Int, @Query("make") makeId: String): Call<Models>

    /**
     * Gets a list of details from a model
     *
     * @param year the year
     * @param makeId the manufacturer ID
     * @param modelName the model name
     *
     * @return a call for the API
     */
    @GET("?callback=?&cmd=getTrims")
    fun getDetails(@Query("year") year: Int,
                   @Query("make") makeId: String,
                   @Query("model") modelName: String): Call<ModelDetails>

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
         * Since the API responses come with a "?(" before the JSON,
         * this interceptor is needed
         */
        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor {
                        val response = it.proceed(it.request())
                        var responseStr = response.body()?.string()
                        responseStr = responseStr?.substring(2, responseStr.length)
                                ?.substring(0, responseStr.length - 4)
                        val mediaType = response.body()?.contentType()
                        response.newBuilder()
                                .body(ResponseBody.create(mediaType, responseStr!!)).build()
                    }.build()
        }
    }

}