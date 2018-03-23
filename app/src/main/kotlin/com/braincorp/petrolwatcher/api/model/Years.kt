package com.braincorp.petrolwatcher.api.model

import com.google.gson.annotations.SerializedName

data class Years(@SerializedName("Years") val range: Range) {

    data class Range(@SerializedName("min_year") val min: Int,
                    @SerializedName("max_year") val max: Int)

}
