package com.braincorp.petrolwatcher.feature.prediction.model

import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.google.firebase.database.DataSnapshot
import java.math.BigDecimal

/**
 * A fuel price prediction
 */
data class Prediction(var id: String = "",
                      var fuelData: Fuel = Fuel(),
                      var city: String = "",
                      var country: String = ""): Parcelable {

    companion object CREATOR : Parcelable.Creator<Prediction> {
        private const val KEY_ID = "id"
        private const val KEY_FUEL_DATA = "fuel_data"
        private const val KEY_CITY = "city"
        private const val KEY_COUNTRY = "country"

        override fun createFromParcel(parcel: Parcel): Prediction = Prediction(parcel)

        override fun newArray(size: Int): Array<Prediction?> = arrayOfNulls(size)
    }

    constructor(snapshot: DataSnapshot): this() {
        with(snapshot) {
            id = child(KEY_ID).value.toString()
            fuelData = Fuel(child(KEY_FUEL_DATA))
            city = child(KEY_CITY).value.toString()
            country = child(KEY_COUNTRY).value.toString()
        }
    }

    constructor(parcel: Parcel): this() {
        with(parcel) {
            id = readString()!!
            city = readString()!!
            country = readString()!!
            val type = readSerializable() as Fuel.Type
            val quality = readSerializable() as Fuel.Quality
            val price = readSerializable() as BigDecimal
            fuelData = Fuel(type, quality, price)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeString(id)
            writeString(city)
            writeString(country)
            writeSerializable(fuelData.type)
            writeSerializable(fuelData.quality)
            writeSerializable(fuelData.price)
        }
    }

    override fun describeContents(): Int = 0

}