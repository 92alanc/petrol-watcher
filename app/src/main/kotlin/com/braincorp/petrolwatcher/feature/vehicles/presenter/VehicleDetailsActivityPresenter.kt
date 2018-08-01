package com.braincorp.petrolwatcher.feature.vehicles.presenter

import com.braincorp.petrolwatcher.feature.vehicles.api.VehicleApi
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Makes
import com.braincorp.petrolwatcher.feature.vehicles.api.model.ModelDetails
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Models
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Years
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The implementation of the presentation layer of the
 * vehicle details activity
 */
class VehicleDetailsActivityPresenter(private val api: VehicleApi,
                                      private val view: VehicleDetailsActivityContract.View)
    : VehicleDetailsActivityContract.Presenter {

    /**
     * Gets the year range available
     * in the API
     */
    override fun getYearRange() {
        api.getYears().enqueue(object : Callback<Years> {
            override fun onFailure(call: Call<Years>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Years>?, response: Response<Years>?) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    val range = responseBody.range
                    view.setYearRange(range.min..range.max)
                }
            }
        })
    }

    /**
     * Gets a list of manufacturers from the API
     * based on a year
     *
     * @param year the year
     */
    override fun getManufacturers(year: Int) {
        api.getMakes(year).enqueue(object : Callback<Makes> {
            override fun onFailure(call: Call<Makes>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Makes>?, response: Response<Makes>?) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    val manufacturers = arrayListOf<String>()
                    responseBody.list.forEach {
                        manufacturers.add(it.name!!)
                    }
                    view.setManufacturerList(manufacturers)
                }
            }
        })
    }

    /**
     * Gets a list of models from the API
     * based on a year and a manufacturer
     *
     * @param year the year
     * @param manufacturer the manufacturer
     */
    override fun getModels(year: Int, manufacturer: String) {
        api.getModels(year, manufacturer).enqueue(object : Callback<Models> {
            override fun onFailure(call: Call<Models>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Models>?, response: Response<Models>?) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    val models = arrayListOf<String>()
                    responseBody.list.forEach {
                        models.add(it.name!!)
                    }
                    view.setModelsList(models)
                }
            }
        })
    }

    /**
     * Gets a model's details from the API
     *
     * @param year the year
     * @param manufacturer the manufacturer
     * @param model the model
     */
    override fun getDetails(year: Int, manufacturer: String, model: String) {
        api.getDetails(year, manufacturer, model).enqueue(object : Callback<ModelDetails> {
            override fun onFailure(call: Call<ModelDetails>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ModelDetails>?, response: Response<ModelDetails>?) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    val trimLevels = arrayListOf<String>()
                    responseBody.list.forEach {
                        trimLevels.add(it.trim!!)
                    }
                    view.setTrimLevelList(trimLevels)
                }
            }
        })
    }

}