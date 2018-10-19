package com.braincorp.petrolwatcher.feature.prediction.model

import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.database.Mappable
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.google.firebase.database.DataSnapshot
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashMap

/**
 * An average fuel price in a location
 */
data class AveragePrice(var price: BigDecimal = BigDecimal.ZERO,
                        var city: String = "",
                        var country: String = "",
                        var fuelType: Fuel.Type = Fuel.Type.PETROL,
                        var fuelQuality: Fuel.Quality = Fuel.Quality.REGULAR) : Parcelable, Mappable {

    companion object CREATOR : Parcelable.Creator<AveragePrice> {
        private const val KEY_ID = "id"
        private const val KEY_PRICE = "price"
        private const val KEY_CITY = "city"
        private const val KEY_COUNTRY = "country"
        private const val KEY_FUEL_TYPE = "fuel_type"
        private const val KEY_FUEL_QUALITY = "fuel_quality"

        override fun createFromParcel(parcel: Parcel?) = AveragePrice(parcel)

        override fun newArray(size: Int): Array<AveragePrice?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel?): this() {
        parcel?.let {
            id = it.readString()!!
            price = BigDecimal(it.readDouble())
            city = it.readString()!!
            country = it.readString()!!
            fuelType = it.readSerializable() as Fuel.Type
            fuelQuality = it.readSerializable() as Fuel.Quality
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with(snapshot) {
            id = child(KEY_ID).value.toString()
            price = BigDecimal(child(KEY_PRICE).value.toString().toDouble())
            city = child(KEY_CITY).value.toString()
            country = child(KEY_COUNTRY).value.toString()
            fuelType = Fuel.Type.valueOf(child(KEY_FUEL_TYPE).value.toString())
            fuelQuality = Fuel.Quality.valueOf(child(KEY_FUEL_QUALITY).value.toString())
        }
    }

    override var id = UUID.randomUUID().toString()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeString(id)
            writeDouble(price.toDouble())
            writeString(city)
            writeString(country)
            writeSerializable(fuelType)
            writeSerializable(fuelQuality)
        }
    }

    override fun describeContents(): Int = 0

    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map[KEY_ID] = id
        map[KEY_PRICE] = price.toDouble()
        map[KEY_CITY] = city
        map[KEY_COUNTRY] = country
        map[KEY_FUEL_TYPE] = fuelType.name
        map[KEY_FUEL_QUALITY] = fuelQuality.name
        return map
    }

}