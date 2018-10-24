package com.braincorp.petrolwatcher.feature.stations.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes
import com.braincorp.petrolwatcher.R
import com.google.firebase.database.DataSnapshot
import java.math.BigDecimal

/**
 * A fuel
 *
 * @param type the type (diesel, ethanol or petrol)
 * @param quality the quality (regular or premium)
 * @param price the price in the local currency
 */
data class Fuel(var type: Type = Type.PETROL,
                var quality: Quality = Quality.REGULAR,
                var price: BigDecimal = BigDecimal("0.0")) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Fuel> {
        private const val KEY_PETROL_REGULAR = "petrol_regular"
        private const val KEY_PETROL_PREMIUM = "petrol_premium"
        private const val KEY_DIESEL = "diesel"
        private const val KEY_ETHANOL = "ethanol"

        override fun createFromParcel(parcel: Parcel): Fuel {
            return Fuel(parcel)
        }

        override fun newArray(size: Int): Array<Fuel?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel): this() {
        with(parcel) {
            type = readSerializable() as Type
            quality = readSerializable() as Quality
            price = BigDecimal(readDouble())
        }
    }

    constructor(snapshot: DataSnapshot): this() {
        with(snapshot) {
            val key = when {
                hasChild(KEY_DIESEL) -> {
                    type = Type.DIESEL
                    quality = Quality.REGULAR
                    KEY_DIESEL
                }

                hasChild(KEY_ETHANOL) -> {
                    type = Type.ETHANOL
                    quality = Quality.REGULAR
                    KEY_ETHANOL
                }

                hasChild(KEY_PETROL_PREMIUM) -> {
                    type = Type.PETROL
                    quality = Quality.PREMIUM
                    KEY_PETROL_PREMIUM
                }

                else -> {
                    type = Type.PETROL
                    quality = Quality.REGULAR
                    KEY_PETROL_REGULAR
                }
            }

            price = BigDecimal(child(key).value.toString())
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeSerializable(type)
            writeSerializable(quality)
            writeDouble(price.toDouble())
        }
    }

    override fun describeContents(): Int = 0

    enum class Type(@StringRes val stringRes: Int) {
        DIESEL(R.string.diesel),
        ETHANOL(R.string.ethanol),
        PETROL(R.string.petrol)
    }

    enum class Quality(@StringRes val stringRes: Int) {
        REGULAR(R.string.regular),
        PREMIUM(R.string.premium)
    }

}