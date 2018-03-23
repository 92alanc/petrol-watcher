package com.braincorp.petrolwatcher.api.model

import com.google.gson.annotations.SerializedName

data class Years(@SerializedName("Years") val core: Core) {

    data class Core(@SerializedName("min_year") val min: Int,
                    @SerializedName("max_year") val max: Int)

}
