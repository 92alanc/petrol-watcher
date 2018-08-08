package com.braincorp.petrolwatcher.feature.vehicles.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import java.util.UUID

/**
 * A vehicle
 *
 * @param id the identifier
 * @param manufacturer the manufacturer
 * @param model the model
 * @param year the year
 * @param trimLevel the trim level, e.g.: 2.0 GTI
 * @param fuelCapacity the fuel capacity, in litres
 * @param avgConsumptionMotorway the average fuel consumption
 *                               in motorways, in km/l
 * @param avgConsumptionCity the average fuel consumption in
 *                           city areas, in km/l
 */
data class Vehicle(var id: String = UUID.randomUUID().toString(),
                   var manufacturer: String = "",
                   var model: String = "",
                   var year: Int = 0,
                   var trimLevel: String = "",
                   var fuelCapacity: Int = 0,
                   var avgConsumptionMotorway: Float = 0f,
                   var avgConsumptionCity: Float = 0f) : Parcelable {

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
            trimLevel = readString()!!
            fuelCapacity = readInt()
            avgConsumptionMotorway = readFloat()
            avgConsumptionCity = readFloat()
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with (snapshot) {
            id = child(KEY_ID).value.toString()
            manufacturer = child(KEY_MANUFACTURER).value.toString()
            model = child(KEY_MODEL).value.toString()
            year = child(KEY_YEAR).value.toString().toInt()
            trimLevel = child(KEY_TRIM_LEVEL).value.toString()
            fuelCapacity = child(KEY_FUEL_CAPACITY).value.toString().toInt()
            avgConsumptionMotorway = child(KEY_CONSUMPTION_MOTORWAY).value.toString().toFloat()
            avgConsumptionCity = child(KEY_CONSUMPTION_CITY).value.toString().toFloat()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with (parcel) {
            writeString(id)
            writeString(manufacturer)
            writeString(model)
            writeInt(year)
            writeString(trimLevel)
            writeInt(fuelCapacity)
            writeFloat(avgConsumptionMotorway)
            writeFloat(avgConsumptionCity)
        }
    }

    override fun describeContents(): Int = 0

    /**
     * Gets the vehicle data as a map
     *
     * @return the vehicle data as a map
     */
    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        map[KEY_ID] = id
        map[KEY_MANUFACTURER] = manufacturer
        map[KEY_MODEL] = model
        map[KEY_YEAR] = year
        map[KEY_TRIM_LEVEL] = trimLevel
        map[KEY_FUEL_CAPACITY] = fuelCapacity
        map[KEY_CONSUMPTION_MOTORWAY] = avgConsumptionMotorway
        map[KEY_CONSUMPTION_CITY] = avgConsumptionCity

        return map
    }

}