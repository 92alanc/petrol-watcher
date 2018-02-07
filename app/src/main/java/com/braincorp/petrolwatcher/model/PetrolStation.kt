package com.braincorp.petrolwatcher.model

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.braincorp.petrolwatcher.utils.fuelFloatMapToStringFloatMap
import com.braincorp.petrolwatcher.utils.stringFloatMapToFuelFloatMap
import com.braincorp.petrolwatcher.utils.stringToRating
import com.google.firebase.database.DataSnapshot
import java.util.*

data class PetrolStation(var id: String = UUID.randomUUID().toString(),
                         var name: String = "",
                         var address: String = "",
                         var prices: Map<Pair<FuelType, FuelQuality>, Float> = emptyMap(),
                         var rating: Rating = Rating.OK) : Parcelable {

    companion object CREATOR : Parcelable.Creator<PetrolStation> {
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PRICES = "prices"
        private const val KEY_RATING = "rating"

        override fun createFromParcel(source: Parcel): PetrolStation {
            return PetrolStation(source)
        }

        override fun newArray(size: Int): Array<PetrolStation?> {
            return arrayOfNulls(size)
        }

    }

    constructor(parcel: Parcel): this() {
        id = parcel.readString()
        name = parcel.readString()
        address = parcel.readString()
        @Suppress("UNCHECKED_CAST")
        prices = parcel.readHashMap(javaClass.classLoader) as Map<Pair<FuelType, FuelQuality>, Float>
        rating = parcel.readSerializable() as Rating
    }

    constructor(snapshot: DataSnapshot): this() {
        id = snapshot.child(KEY_ID).value.toString()
        name = snapshot.child(KEY_NAME).value.toString()
        address = snapshot.child(KEY_ADDRESS).value.toString()
        @Suppress("UNCHECKED_CAST")
        prices = stringFloatMapToFuelFloatMap(snapshot.child(KEY_PRICES).value as Map<String, Float>)
        rating = stringToRating(snapshot.value.toString())
    }

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map[KEY_ID] = id
        map[KEY_NAME] = name
        map[KEY_ADDRESS] = address
        map[KEY_PRICES] = fuelFloatMapToStringFloatMap(prices)
        map[KEY_RATING] = rating
        return map
    }

    fun allFieldsAreValid(): Boolean {
        return !TextUtils.isEmpty(name)
               && !TextUtils.isEmpty(address)
               && prices.isNotEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeMap(prices)
        parcel.writeSerializable(rating)
    }

    override fun describeContents(): Int = 0

}