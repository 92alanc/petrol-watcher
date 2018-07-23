package com.braincorp.petrolwatcher.feature.vehicles.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Models(@SerializedName("Models") val list: List<Model>) {

    data class Model(@SerializedName("model_name") var name: String? = null,
                     @SerializedName("model_make_id") var makeId: String? = null) : Parcelable {

        companion object CREATOR : Parcelable.Creator<Model> {
            override fun createFromParcel(source: Parcel): Model {
                return Model(source)
            }

            override fun newArray(size: Int): Array<Model?> {
                return arrayOfNulls(size)
            }
        }

        constructor(parcel: Parcel): this() {
            name = parcel.readString()
            makeId = parcel.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(name)
            dest.writeString(makeId)
        }

        override fun describeContents(): Int = 0

    }

}