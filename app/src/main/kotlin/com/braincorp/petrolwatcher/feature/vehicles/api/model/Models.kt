package com.braincorp.petrolwatcher.feature.vehicles.api.model

import com.google.gson.annotations.SerializedName

data class Models(@SerializedName("Models") val list: List<Model>) {

    data class Model(@SerializedName("model_name") val name: String,
                     @SerializedName("model_make_id") val makeId: String)

}
