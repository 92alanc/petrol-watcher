package com.braincorp.petrolwatcher.feature.stations.model

import android.os.Parcel
import android.os.Parcelable
import com.braincorp.petrolwatcher.database.Mappable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap

/**
 * A petrol station
 *
 * @param name the name
 * @param address the address
 * @param latLng the latitude and longitude
 * @param rating the rating (from 0 to 5)
 */
data class PetrolStation(var name: String = "",
                         var address: String = "",
                         var latLng: LatLng = LatLng(0.0, 0.0),
                         var rating: Int = 0) : Mappable, Parcelable {

    companion object CREATOR : Parcelable.Creator<PetrolStation> {
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_ADDRESS = "address"
        private const val KEY_LAT = "lat"
        private const val KEY_LNG = "lng"
        private const val KEY_RATING = "rating"

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
            val lat = readDouble()
            val lng = readDouble()
            latLng = LatLng(lat, lng)
            rating = readInt()
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with (snapshot) {
            id = child(KEY_ID).value.toString()
            name = child(KEY_NAME).value.toString()
            address = child(KEY_ADDRESS).value.toString()
            val lat = child(KEY_LAT).value.toString().toDouble()
            val lng = child(KEY_LNG).value.toString().toDouble()
            latLng = LatLng(lat, lng)
            rating = child(KEY_RATING).value.toString().toInt()
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
        map[KEY_LAT] = latLng.latitude
        map[KEY_LNG] = latLng.longitude
        map[KEY_RATING] = rating
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
            writeDouble(latLng.latitude)
            writeDouble(latLng.longitude)
            writeInt(rating)
        }
    }

    override fun describeContents(): Int = 0

}