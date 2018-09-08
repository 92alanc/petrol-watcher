package com.braincorp.petrolwatcher.feature.stations.model

import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.database.Mappable
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap

/**
 * A petrol station
 *
 * @param name the name
 * @param address the address
 */
data class PetrolStation(var name: String = "",
                         var address: String = "") : Mappable, Parcelable {

    companion object CREATOR : Parcelable.Creator<PetrolStation> {
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_ADDRESS = "address"

        override fun createFromParcel(parcel: Parcel): PetrolStation {
            return PetrolStation(parcel)
        }

        override fun newArray(size: Int): Array<PetrolStation?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel): this() {
        with (parcel) {
            id = readString()
            name = readString()
            address = readString()
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with (snapshot) {
            id = child(KEY_ID).value.toString()
            name = child(KEY_NAME).value.toString()
            address = child(KEY_ADDRESS).value.toString()
        }
    }

    override var id: String = UUID.randomUUID().toString()

    /**
     * Converts the object to a map
     *
     * @return the map
     */
    override fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map[KEY_ID] = id
        map[KEY_NAME] = name
        map[KEY_ADDRESS] = address
        return map
    }

    /**
     * Determines whether the data is valid
     *
     * @return true if positive, otherwise false
     */
    fun isValid(): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with (parcel) {
            writeString(id)
            writeString(name)
            writeString(address)
        }
    }

    override fun describeContents(): Int = 0

}