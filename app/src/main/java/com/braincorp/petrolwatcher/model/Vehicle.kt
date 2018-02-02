package com.braincorp.petrolwatcher.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.utils.stringToFuelType
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.ArrayList

data class Vehicle(var id: String = UUID.randomUUID().toString(),
                   var manufacturer: String = "", var name: String = "",
                   var year: Int = 0,
                   var vehicleType: VehicleType = VehicleType.CAR,
                   var fuelTypes: ArrayList<FuelType> = ArrayList(),
                   var kmPerLitre: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_NAME = "name"
        private const val KEY_YEAR = "year"
        private const val KEY_KM_PER_LITRE = "km_per_litre"
        private const val KEY_VEHICLE_TYPE = "vehicle_type"
        private const val KEY_FUEL_TYPES = "fuel_types"

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

    constructor(snapshot: DataSnapshot): this() {
        id = snapshot.child(KEY_ID).value.toString()
        name = snapshot.child(KEY_NAME).value.toString()
        manufacturer = snapshot.child(KEY_MANUFACTURER).value.toString()
        year = snapshot.child(KEY_YEAR).value.toString().toInt()
        kmPerLitre = snapshot.child(KEY_KM_PER_LITRE).value.toString().toFloat()
        vehicleType = snapshot.child(KEY_VEHICLE_TYPE).getValue(VehicleType::class.java)!!
        snapshot.child(KEY_FUEL_TYPES).children.forEach {
            fuelTypes.add(stringToFuelType(it.value.toString()))
        }
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
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
        map[KEY_ID] = id
        map[KEY_MANUFACTURER] = manufacturer
        map[KEY_NAME] = name
        map[KEY_YEAR] = year
        map[KEY_VEHICLE_TYPE] = vehicleType
        map[KEY_FUEL_TYPES] = fuelTypes
        map[KEY_KM_PER_LITRE] = kmPerLitre
        return map
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
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