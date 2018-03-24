package com.braincorp.petrolwatcher.feature.petrolstations.model

import android.os.Parcel
import android.os.Parcelable

data class Fuel(var type: Type = Type.PETROL,
                var quality: Quality = Quality.REGULAR,
                var price: Float = 0f) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Fuel> {
        override fun createFromParcel(source: Parcel?): Fuel = Fuel(
                source)

        override fun newArray(size: Int): Array<Fuel?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel?): this() {
        if (parcel != null) {
            type = parcel.readSerializable() as Type
            quality = parcel.readSerializable() as Quality
            price = parcel.readFloat()
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeSerializable(type)
        dest?.writeSerializable(quality)
        dest?.writeFloat(price)
    }

    override fun describeContents(): Int = 0

    enum class Type {
        DIESEL,
        ETHANOL,
        LPG,
        PETROL
    }

    enum class Quality {
        REGULAR,
        PREMIUM
    }

}