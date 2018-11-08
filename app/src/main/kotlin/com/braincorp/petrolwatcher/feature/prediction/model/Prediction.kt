package com.braincorp.petrolwatcher.feature.prediction.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import java.math.BigDecimal
import java.util.*

/**
 * A fuel price prediction
 */
data class Prediction(var area: String = "",
                      var prices: HashMap<String, BigDecimal> = hashMapOf()) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Prediction> {
        const val KEY_REGULAR_PETROL = "petrol_regular"
        const val KEY_PREMIUM_PETROL = "petrol_premium"
        const val KEY_DIESEL = "diesel"
        const val KEY_ETHANOL = "ethanol"

        private const val KEY_AREA = "area"

        override fun createFromParcel(source: Parcel): Prediction = Prediction(source)

        override fun newArray(size: Int): Array<Prediction?> = arrayOfNulls(size)
    }

    @Suppress("UNCHECKED_CAST")
    constructor(parcel: Parcel): this() {
        with(parcel) {
            area = readString()!!
            prices = hashMapOf()
            prices[KEY_REGULAR_PETROL] = BigDecimal(readString()!!)
            prices[KEY_PREMIUM_PETROL] = BigDecimal(readString()!!)
            prices[KEY_DIESEL] = BigDecimal(readString()!!)
            prices[KEY_ETHANOL] = BigDecimal(readString()!!)
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with(snapshot) {
            area = child(KEY_AREA).value.toString()
            prices = hashMapOf()
            prices[KEY_REGULAR_PETROL] = BigDecimal(child(KEY_REGULAR_PETROL).value.toString())
            prices[KEY_PREMIUM_PETROL] = BigDecimal(child(KEY_PREMIUM_PETROL).value.toString())
            prices[KEY_DIESEL] = BigDecimal(child(KEY_DIESEL).value.toString())
            prices[KEY_ETHANOL] = BigDecimal(child(KEY_ETHANOL).value.toString())
        }
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(area)
            writeString(prices[KEY_REGULAR_PETROL]!!.toEngineeringString())
            writeString(prices[KEY_PREMIUM_PETROL]!!.toEngineeringString())
            writeString(prices[KEY_ETHANOL]!!.toEngineeringString())
            writeString(prices[KEY_DIESEL]!!.toEngineeringString())
        }
    }

    override fun describeContents(): Int = 0

}