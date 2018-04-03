package com.braincorp.petrolwatcher.feature.vehicles.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap

data class Vehicle(var id: String = UUID.randomUUID().toString(),
                   var manufacturer: String = "",
                   var name: String = "",
                   var year: Int = 0,
                   var fuelCapacity: Int = 0,
                   var litresPer100KmMotorway: Float = 0f,
                   var litresPer100KmCity: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_NAME = "name"
        private const val KEY_YEAR = "year"
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
        fuelCapacity = parcel.readInt()
        litresPer100KmMotorway = parcel.readFloat()
        litresPer100KmCity = parcel.readFloat()
    }

    constructor(snapshot: DataSnapshot): this() {
        id = snapshot.child(KEY_ID).value.toString()
        manufacturer = snapshot.child(KEY_MANUFACTURER).value.toString()
        name = snapshot.child(KEY_NAME).value.toString()
        year = snapshot.child(KEY_YEAR).value.toString().toInt()
        fuelCapacity = snapshot.child(KEY_FUEL_CAPACITY).value.toString().toInt()
        litresPer100KmMotorway = snapshot.child(KEY_CONSUMPTION_MOTORWAY).value.toString().toFloat()
        litresPer100KmCity = snapshot.child(KEY_CONSUMPTION_CITY).value.toString().toFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(manufacturer)
        parcel.writeString(name)
        parcel.writeInt(year)
        parcel.writeInt(fuelCapacity)
        parcel.writeFloat(litresPer100KmMotorway)
        parcel.writeFloat(litresPer100KmCity)
    }

    override fun describeContents(): Int = 0

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()

        map[KEY_ID] = id
        map[KEY_MANUFACTURER] = manufacturer
        map[KEY_NAME] = name
        map[KEY_YEAR] = year
        map[KEY_FUEL_CAPACITY] = fuelCapacity
        map[KEY_CONSUMPTION_MOTORWAY] = litresPer100KmMotorway
        map[KEY_CONSUMPTION_CITY] = litresPer100KmCity

        return map
    }

}