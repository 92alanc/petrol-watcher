package com.braincorp.petrolwatcher.api.model

import com.google.gson.annotations.SerializedName

data class Years(@SerializedName("min_year") val min: String,
                 @SerializedName("max_year") val max: String)
