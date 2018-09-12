package com.braincorp.petrolwatcher.feature.stations.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes
import com.braincorp.petrolwatcher.R
import java.math.BigDecimal

/**
 * A fuel
 *
 * @param type the type (diesel, ethanol, LPG or petrol)
 * @param quality the quality (regular or premium)
 * @param price the price in the local currency
 */
data class Fuel(var type: Type = Type.PETROL,
                var quality: Quality = Quality.REGULAR,
                var price: BigDecimal = BigDecimal("0.0")) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Fuel> {
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
        LPG(R.string.lpg),
        PETROL(R.string.petrol)
    }

    enum class Quality(@StringRes val stringRes: Int) {
        REGULAR(R.string.regular),
        PREMIUM(R.string.premium)
    }

}