package com.braincorp.petrolwatcher.feature.vehicles.model

import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.database.Mappable
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * A vehicle
 *
 * @param id the identifier
 * @param manufacturer the manufacturer
 * @param model the model
 * @param year the year
 * @param details the extra details
 */
data class Vehicle(var manufacturer: String = "",
                   var model: String = "",
                   var year: Int = 0,
                   var details: Details = Details()) : Parcelable, Mappable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_MODEL = "model"
        private const val KEY_YEAR = "year"
        private const val KEY_TRIM_LEVEL = "trim_level"
        private const val KEY_FUEL_CAPACITY = "fuel_capacity"
        private const val KEY_CONSUMPTION_MOTORWAY = "consumption_motorway"
        private const val KEY_CONSUMPTION_CITY = "consumption_city"

        override fun createFromParcel(source: Parcel): Vehicle {
            return Vehicle(source)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel): this() {
        with (parcel) {
            id = readString()!!
            manufacturer = readString()!!
            model = readString()!!
            year = readInt()
            details = readParcelable(javaClass.classLoader)
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with (snapshot) {
            id = child(KEY_ID).value.toString()
            manufacturer = child(KEY_MANUFACTURER).value.toString()
            model = child(KEY_MODEL).value.toString()
            year = child(KEY_YEAR).value.toString().toInt()
            details.trimLevel = child(KEY_TRIM_LEVEL).value.toString()
            details.fuelCapacity = child(KEY_FUEL_CAPACITY).value.toString().toInt()
            details.avgConsumptionMotorway = child(KEY_CONSUMPTION_MOTORWAY).value.toString().toFloat()
            details.avgConsumptionCity = child(KEY_CONSUMPTION_CITY).value.toString().toFloat()
        }
    }

    override var id: String = UUID.randomUUID().toString()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with (parcel) {
            writeString(id)
            writeString(manufacturer)
            writeString(model)
            writeInt(year)
            writeParcelable(details, flags)
        }
    }

    override fun describeContents(): Int = 0

    /**
     * Converts the object to a map
     *
     * @return the map
     */
    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        map[KEY_ID] = id
        map[KEY_MANUFACTURER] = manufacturer
        map[KEY_MODEL] = model
        map[KEY_YEAR] = year
        map[KEY_TRIM_LEVEL] = details.trimLevel
        map[KEY_FUEL_CAPACITY] = details.fuelCapacity
        map[KEY_CONSUMPTION_MOTORWAY] = details.avgConsumptionMotorway
        map[KEY_CONSUMPTION_CITY] = details.avgConsumptionCity

        return map
    }

    /**
     * Determines whether the vehicle data is valid
     *
     * @return true if positive, otherwise false
     */
    fun isValid(): Boolean {
        return manufacturer.isNotBlank() &&
                model.isNotBlank() &&
                year > 0 &&
                details.trimLevel.isNotBlank() &&
                details.fuelCapacity > 0 &&
                details.avgConsumptionCity > 0f &&
                details.avgConsumptionMotorway > 0f
    }

    data class Details(var trimLevel: String = "",
                       var fuelCapacity: Int = 0,
                       var avgConsumptionCity: Float = 0f,
                       var avgConsumptionMotorway: Float = 0f) : Parcelable {

        companion object CREATOR : Parcelable.Creator<Details> {
            override fun createFromParcel(parcel: Parcel): Details {
                return Details(parcel)
            }

            override fun newArray(size: Int): Array<Details?> = arrayOfNulls(size)
        }

        constructor(parcel: Parcel): this() {
            with (parcel) {
                writeString(trimLevel)
                writeInt(fuelCapacity)
                writeFloat(avgConsumptionCity)
                writeFloat(avgConsumptionMotorway)
            }
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            with (parcel) {
                writeString(trimLevel)
                writeInt(fuelCapacity)
                writeFloat(avgConsumptionCity)
                writeFloat(avgConsumptionMotorway)
            }
        }

        override fun describeContents(): Int = 0

    }

}