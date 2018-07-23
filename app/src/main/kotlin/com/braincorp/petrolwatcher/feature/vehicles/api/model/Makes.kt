package com.braincorp.petrolwatcher.feature.vehicles.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Makes(@SerializedName("Makes") val list: List<Make>) {

    data class Make(@SerializedName("make_id") var id: String? = null,
                    @SerializedName("make_display") var name: String? = null,
                    @SerializedName("make_is_common") var isCommon: Int = -1,
                    @SerializedName("make_country") var country: String? = null): Parcelable {

        companion object CREATOR : Parcelable.Creator<Make> {
            override fun createFromParcel(source: Parcel): Make {
                return Make(source)
            }

            override fun newArray(size: Int): Array<Make?> {
                return arrayOfNulls(size)
            }
        }

        constructor(parcel: Parcel): this() {
            id = parcel.readString()
            name = parcel.readString()
            isCommon = parcel.readInt()
            country = parcel.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(id)
            dest.writeString(name)
            dest.writeInt(isCommon)
            dest.writeString(country)
        }

        override fun describeContents(): Int = 0

    }

}