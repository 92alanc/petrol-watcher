package com.braincorp.petrolwatcher.feature.vehicles.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Years(@SerializedName("Years") val range: Range) {

    data class Range(@SerializedName("min_year") var min: Int = -1,
                     @SerializedName("max_year") var max: Int = -1) : Parcelable {

        companion object CREATOR : Parcelable.Creator<Range> {
            override fun createFromParcel(source: Parcel): Range {
                return Range(source)
            }

            override fun newArray(size: Int): Array<Range?> {
                return arrayOfNulls(size)
            }
        }

        constructor(parcel: Parcel): this() {
            min = parcel.readInt()
            max = parcel.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(min)
            dest.writeInt(max)
        }

        override fun describeContents(): Int = 0

        fun toIntRange(): IntRange = min..max

    }

}