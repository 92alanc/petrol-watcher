package com.braincorp.petrolwatcher.feature.vehicles.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ModelDetails(@SerializedName("Trims") val list: List<Trims>) {

    data class Trims(@SerializedName("model_id") var modelId: Int = -1,
                     @SerializedName("model_make_id") var makeId: String? = null,
                     @SerializedName("model_name") var name: String? = null,
                     @SerializedName("model_trim") var trim: String? = null,
                     @SerializedName("model_year") var year: Int = -1,
                     @SerializedName("model_body") var body: String? = null,
                     @SerializedName("model_engine_position") var enginePosition: String? = null,
                     @SerializedName("model_engine_cc") var cc: Int = -1,
                     @SerializedName("model_engine_cyl") var cylinders: Int = -1,
                     @SerializedName("model_engine_type") var engineType: String? = null,
                     @SerializedName("model_engine_valves_per_cyl") var valvesPerCylinder: Int = -1,
                     @SerializedName("model_engine_power_ps") var enginePowerPs: Int = -1,
                     @SerializedName("model_engine_power_rpm") var enginePowerRpm: Int = -1,
                     @SerializedName("model_engine_torque_nm") var engineTorqueNm: Int = -1,
                     @SerializedName("model_engine_torque_rpm") var engineTorqueRpm: Int = -1,
                     @SerializedName("model_engine_bore_mm") var engineBoreMm: Float = -1f,
                     @SerializedName("model_engine_stroke_mm") var engineStrokeMm: Float = -1f,
                     @SerializedName("model_engine_compression") var engineCompression: String? = null,
                     @SerializedName("model_engine_fuel") var fuelType: String? = null,
                     @SerializedName("model_top_speed_kph") var topSpeedKmPh: Int = -1,
                     @SerializedName("model_0_to_100_kph") var seconds0To100: Float = -1f,
                     @SerializedName("model_drive") var drive: String? = null,
                     @SerializedName("model_transmission_type") var transmissionType: String? = null,
                     @SerializedName("model_seats") var seats: Int = -1,
                     @SerializedName("model_doors") var doors: Int = -1,
                     @SerializedName("model_weight_kg") var weightInKg: Int = -1,
                     @SerializedName("model_length_mm") var lengthInMm: Int = -1,
                     @SerializedName("model_width_mm") var widthInMm: Int = -1,
                     @SerializedName("model_height_mm") var heightInMm: Int = -1,
                     @SerializedName("model_wheelbase_mm") var wheelbaseInMm: Int = -1,
                     @SerializedName("model_lkm_hwy") var litresPer100KmMotorway: Int = -1,
                     @SerializedName("model_lkm_city") var litresPer100KmCity: Int = -1,
                     @SerializedName("model_lkm_mixed") var litresPer100KmMixed: Float = -1f,
                     @SerializedName("model_fuel_cap_l") var fuelCapacityLitres: Int = -1,
                     @SerializedName("model_sold_in_us") var soldInUsa: Int = -1,
                     @SerializedName("model_co2") var co2: String? = null,
                     @SerializedName("model_make_display") var manufacturerName: String? = null,
                     @SerializedName("make_display") var makeDisplay: String? = null,
                     @SerializedName("make_country") var country: String? = null) : Parcelable {

        companion object CREATOR: Parcelable.Creator<Trims> {
            override fun createFromParcel(source: Parcel): Trims {
                return Trims(source)
            }

            override fun newArray(size: Int): Array<Trims?> {
                return arrayOfNulls(size)
            }
        }

        constructor(parcel: Parcel): this() {
            modelId = parcel.readInt()
            makeId = parcel.readString()
            name = parcel.readString()
            trim = parcel.readString()
            year = parcel.readInt()
            body = parcel.readString()
            enginePosition = parcel.readString()
            cc = parcel.readInt()
            cylinders = parcel.readInt()
            engineType = parcel.readString()
            valvesPerCylinder = parcel.readInt()
            enginePowerPs = parcel.readInt()
            enginePowerRpm = parcel.readInt()
            engineTorqueNm = parcel.readInt()
            engineTorqueRpm = parcel.readInt()
            engineBoreMm = parcel.readFloat()
            engineStrokeMm = parcel.readFloat()
            engineCompression = parcel.readString()
            fuelType = parcel.readString()
            topSpeedKmPh = parcel.readInt()
            seconds0To100 = parcel.readFloat()
            drive = parcel.readString()
            transmissionType = parcel.readString()
            seats = parcel.readInt()
            doors = parcel.readInt()
            weightInKg = parcel.readInt()
            lengthInMm = parcel.readInt()
            widthInMm = parcel.readInt()
            heightInMm = parcel.readInt()
            wheelbaseInMm = parcel.readInt()
            litresPer100KmMotorway = parcel.readInt()
            litresPer100KmCity = parcel.readInt()
            fuelCapacityLitres = parcel.readInt()
            soldInUsa = parcel.readInt()
            co2 = parcel.readString()
            manufacturerName = parcel.readString()
            makeDisplay = parcel.readString()
            country = parcel.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(modelId)
            dest.writeString(makeId)
            dest.writeString(name)
            dest.writeString(trim)
            dest.writeInt(year)
            dest.writeString(body)
            dest.writeString(enginePosition)
            dest.writeInt(cc)
            dest.writeInt(cylinders)
            dest.writeString(engineType)
            dest.writeInt(valvesPerCylinder)
            dest.writeInt(enginePowerPs)
            dest.writeInt(enginePowerRpm)
            dest.writeInt(engineTorqueNm)
            dest.writeInt(engineTorqueRpm)
            dest.writeFloat(engineBoreMm)
            dest.writeFloat(engineStrokeMm)
            dest.writeString(engineCompression)
            dest.writeString(fuelType)
            dest.writeInt(topSpeedKmPh)
            dest.writeFloat(seconds0To100)
            dest.writeString(drive)
            dest.writeString(transmissionType)
            dest.writeInt(seats)
            dest.writeInt(doors)
            dest.writeInt(weightInKg)
            dest.writeInt(lengthInMm)
            dest.writeInt(widthInMm)
            dest.writeInt(heightInMm)
            dest.writeInt(wheelbaseInMm)
            dest.writeInt(litresPer100KmMotorway)
            dest.writeInt(litresPer100KmCity)
            dest.writeInt(fuelCapacityLitres)
            dest.writeInt(soldInUsa)
            dest.writeString(co2)
            dest.writeString(manufacturerName)
            dest.writeString(makeDisplay)
            dest.writeString(country)
        }

        override fun describeContents(): Int = 0

    }

}
