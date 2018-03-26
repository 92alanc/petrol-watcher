package com.braincorp.petrolwatcher.feature.vehicles.api.model

import com.google.gson.annotations.SerializedName

data class Make(@SerializedName("make_id") val id: String,
                @SerializedName("make_display") val name: String,
                @SerializedName("make_is_common") val isCommon: Int,
                @SerializedName("make_country") val country: String)
