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
 * @param name the name
 * @param year the year
 * @param trims the trims, e.g.: 2.0 GTI
 * @param fuelCapacity the fuel capacity, in litres
 * @param avgConsumptionMotorway the average fuel consumption
 *                               in motorways, in km/l
 * @param avgConsumptionCity the average fuel consumption in
 *                           city areas, in km/l
 */
data class Vehicle(var id: String = UUID.randomUUID().toString(),
                   var manufacturer: String = "",
                   var name: String = "",
                   var year: Int = 0,
                   var trims: String = "",
                   var fuelCapacity: Int = 0,
                   var avgConsumptionMotorway: Float = 0f,
                   var avgConsumptionCity: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_NAME = "name"
        private const val KEY_YEAR = "year"
        private const val KEY_TRIMS = "trims"
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
        id = parcel.readString()
        manufacturer = parcel.readString()
        name = parcel.readString()
        year = parcel.readInt()
        trims = parcel.readString()
        fuelCapacity = parcel.readInt()
        avgConsumptionMotorway = parcel.readFloat()
        avgConsumptionCity = parcel.readFloat()
    }

    constructor(snapshot: DataSnapshot): this() {
        id = snapshot.child(KEY_ID).value.toString()
        manufacturer = snapshot.child(KEY_MANUFACTURER).value.toString()
        name = snapshot.child(KEY_NAME).value.toString()
        year = snapshot.child(KEY_YEAR).value.toString().toInt()
        trims = snapshot.child(KEY_TRIMS).value.toString()
        fuelCapacity = snapshot.child(KEY_FUEL_CAPACITY).value.toString().toInt()
        avgConsumptionMotorway = snapshot.child(KEY_CONSUMPTION_MOTORWAY).value.toString().toFloat()
        avgConsumptionCity = snapshot.child(KEY_CONSUMPTION_CITY).value.toString().toFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(manufacturer)
        parcel.writeString(name)
        parcel.writeInt(year)
        parcel.writeString(trims)
        parcel.writeInt(fuelCapacity)
        parcel.writeFloat(avgConsumptionMotorway)
        parcel.writeFloat(avgConsumptionCity)
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
        map[KEY_NAME] = name
        map[KEY_YEAR] = year
        map[KEY_TRIMS] = trims
        map[KEY_FUEL_CAPACITY] = fuelCapacity
        map[KEY_CONSUMPTION_MOTORWAY] = avgConsumptionMotorway
        map[KEY_CONSUMPTION_CITY] = avgConsumptionCity

        return map
    }

}