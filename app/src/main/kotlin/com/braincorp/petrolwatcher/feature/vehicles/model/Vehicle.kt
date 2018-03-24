package com.braincorp.petrolwatcher.feature.vehicles.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils.isEmpty
import com.braincorp.petrolwatcher.feature.petrolstations.model.Fuel
import com.braincorp.petrolwatcher.utils.stringToFuelType
import com.google.firebase.database.DataSnapshot
import java.util.*
import java.util.Calendar.YEAR
import kotlin.collections.ArrayList

data class Vehicle(var id: String = UUID.randomUUID().toString(),
                   var manufacturer: String = "", var name: String = "",
                   var year: Int = 0,
                   var vehicleType: Type = Type.CAR,
                   var fuelTypes: ArrayList<Fuel.Type> = ArrayList(),
                   var fuelConsumption: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        private const val KEY_ID = "id"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_NAME = "name"
        private const val KEY_YEAR = "year"
        private const val KEY_FUEL_CONSUMPTION = "fuel_consumption"
        private const val KEY_VEHICLE_TYPE = "vehicle_type"
        private const val KEY_FUEL_TYPES = "fuel_types"

        private const val KEY_LPG = "lpg"
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
        fuelConsumption = snapshot.child(KEY_FUEL_CONSUMPTION).value.toString().toFloat()
        vehicleType = snapshot.child(KEY_VEHICLE_TYPE).getValue(
                Type::class.java)!!
        snapshot.child(KEY_FUEL_TYPES).children.forEach {
            fuelTypes.add(stringToFuelType(it.value.toString()))
        }
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        manufacturer = parcel.readString()
        name = parcel.readString()
        year = parcel.readInt()
        vehicleType = parcel.readSerializable() as Type

        val bundle = parcel.readBundle(javaClass.classLoader)
        if (bundle.containsKey(KEY_LPG)) fuelTypes.add(Fuel.Type.LPG)
        if (bundle.containsKey(KEY_DIESEL)) fuelTypes.add(Fuel.Type.DIESEL)
        if (bundle.containsKey(KEY_ETHANOL)) fuelTypes.add(Fuel.Type.ETHANOL)
        if (bundle.containsKey(KEY_PETROL)) fuelTypes.add(Fuel.Type.PETROL)

        fuelConsumption = parcel.readFloat()
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
            val sameKmPerLitre = fuelConsumption == otherVehicle.fuelConsumption

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
        map[KEY_FUEL_CONSUMPTION] = fuelConsumption
        return map
    }

    fun allFieldsAreValid(): Boolean {
        return !isEmpty(manufacturer)
                && !isEmpty(name)
                && year > 1900 && year <= Calendar.getInstance().get(YEAR)
                && fuelTypes.isNotEmpty()
                && fuelConsumption > 0f
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
                Fuel.Type.LPG -> KEY_LPG
                Fuel.Type.DIESEL -> KEY_DIESEL
                Fuel.Type.ETHANOL -> KEY_ETHANOL
                Fuel.Type.PETROL -> KEY_PETROL
            }
            bundle.putSerializable(key, it)
        }
        parcel.writeBundle(bundle)

        parcel.writeFloat(fuelConsumption)
    }

    override fun describeContents(): Int = 0

    enum class Type {
        MOTORCYCLE,
        CAR,
        VAN,
        LORRY
    }

}