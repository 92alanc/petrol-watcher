package com.braincorp.petrolwatcher.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

data class Vehicle(var manufacturer: String = "", var name: String = "",
                   var year: Int = 0,
                   var vehicleType: VehicleType = VehicleType.CAR,
                   var fuelTypes: ArrayList<FuelType> = ArrayList(),
                   var kmPerLitre: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_AUTOGAS = "autogas"
        private const val KEY_DIESEL = "diesel"
        private const val KEY_ETHANOL = "ethanol"
        private const val KEY_PETROL = "petrol"

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

        val bundle = parcel.readBundle(javaClass.classLoader)
        if (bundle.containsKey(KEY_AUTOGAS)) fuelTypes.add(FuelType.AUTOGAS)
        if (bundle.containsKey(KEY_DIESEL)) fuelTypes.add(FuelType.DIESEL)
        if (bundle.containsKey(KEY_ETHANOL)) fuelTypes.add(FuelType.ETHANOL)
        if (bundle.containsKey(KEY_PETROL)) fuelTypes.add(FuelType.PETROL)

        kmPerLitre = parcel.readFloat()
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

        val bundle = Bundle()
        fuelTypes.forEach {
            val key = when (it) {
                FuelType.AUTOGAS -> KEY_AUTOGAS
                FuelType.DIESEL -> KEY_DIESEL
                FuelType.ETHANOL -> KEY_ETHANOL
                FuelType.PETROL -> KEY_PETROL
            }
            bundle.putSerializable(key, it)
        }
        parcel.writeBundle(bundle)

        parcel.writeFloat(kmPerLitre)
    }


    override fun describeContents(): Int {
        return 0
    }

}