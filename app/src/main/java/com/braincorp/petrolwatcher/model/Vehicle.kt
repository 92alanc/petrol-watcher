package com.braincorp.petrolwatcher.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

data class Vehicle(var manufacturer: String = "", var name: String = "",
                   var year: Int = 0,
                   var vehicleType: VehicleType = VehicleType.CAR,
                   var fuelTypes: List<FuelType> = emptyList(),
                   var kmPerLitre: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val AUTOGAS_INT = 1
        private const val DIESEL_INT = 2
        private const val ETHANOL_INT = 3
        private const val PETROL_INT = 4

        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }

    val id = UUID.randomUUID().toString()

    constructor(parcel: Parcel) : this() {
        manufacturer = parcel.readString()
        name = parcel.readString()
        year = parcel.readInt()
        vehicleType = parcel.readSerializable() as VehicleType

        @Suppress("UNCHECKED_CAST")
        fuelTypes = parcel.readArrayList(javaClass.classLoader) as ArrayList<FuelType> // FIXME

        kmPerLitre = parcel.readFloat() // FIXME
    }

    override fun equals(other: Any?): Boolean {
        val sameObject = other is Vehicle
        return if (other != null) {
            val otherVehicle = other as Vehicle
            val sameName = name == otherVehicle.name
            val sameFuelType = fuelTypes
                    .filterIndexed { i, fuelType -> fuelType != otherVehicle.fuelTypes[i] }
                    .none()
            val sameYear = year == otherVehicle.year
            val sameKmPerLitre = kmPerLitre == otherVehicle.kmPerLitre

            sameName && sameFuelType && sameYear && sameKmPerLitre
        } else {
            sameObject
        }
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["id"] = id
        map["manufacturer"] = manufacturer
        map["name"] = name
        map["year"] = year
        map["vehicle_type"] = vehicleType
        map["fuel_types"] = fuelTypes
        map["km_per_litre"] = kmPerLitre
        return map
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(manufacturer)
        parcel.writeString(name)
        parcel.writeInt(year)
        parcel.writeSerializable(vehicleType)

        val typesList = ArrayList<Int>()
        fuelTypes.forEach {
            when (it) {
                FuelType.AUTOGAS -> typesList.add(AUTOGAS_INT)
                FuelType.DIESEL -> typesList.add(DIESEL_INT)
                FuelType.ETHANOL -> typesList.add(ETHANOL_INT)
                FuelType.PETROL -> typesList.add(PETROL_INT)
            }
        }
        parcel.writeList(typesList)

        parcel.writeFloat(kmPerLitre)
    }

    override fun describeContents(): Int {
        return 0
    }

}