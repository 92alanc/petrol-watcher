package com.braincorp.petrolwatcher.feature.vehicles.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class NewVehicleModel(var id: String = UUID.randomUUID().toString(),
                           var manufacturer: String = "",
                           var name: String = "",
                           var year: Int = 0,
                           var fuelCapacity: Int = 0,
                           var litresPer100KmMotorway: Int = 0,
                           var litresPer100KmCity: Int = 0) : Parcelable {

    companion object CREATOR : Parcelable.Creator<NewVehicleModel> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_NAME = "name"
        private const val KEY_YEAR = "year"
        private const val KEY_FUEL_CAPACITY = "fuel_capacity"
        private const val KEY_CONSUMPTION_MOTORWAY = "consumption_motorway"
        private const val KEY_CONSUMPTION_CITY = "consumption_city"

        override fun createFromParcel(source: Parcel): NewVehicleModel {
            return NewVehicleModel(source)
        }

        override fun newArray(size: Int): Array<NewVehicleModel?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel): this() {
        id = parcel.readString()
        manufacturer = parcel.readString()
        name = parcel.readString()
        year = parcel.readInt()
        fuelCapacity = parcel.readInt()
        litresPer100KmMotorway = parcel.readInt()
        litresPer100KmCity = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(manufacturer)
        parcel.writeString(name)
        parcel.writeInt(year)
        parcel.writeInt(fuelCapacity)
        parcel.writeInt(litresPer100KmMotorway)
        parcel.writeInt(litresPer100KmCity)
    }

    override fun describeContents(): Int = 0

}