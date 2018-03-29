package com.braincorp.petrolwatcher.feature.vehicles.api.model

import com.google.gson.annotations.SerializedName

data class ModelDetails(@SerializedName("Trims") val list: List<Trims>) {

    data class Trims(@SerializedName("model_id") val modelId: Int,
                     @SerializedName("model_make_id") val makeId: String,
                     @SerializedName("model_name") val name: String,
                     @SerializedName("model_trim") val trim: String,
                     @SerializedName("model_year") val year: Int,
                     @SerializedName("model_body") val body: String,
                     @SerializedName("model_engine_position") val enginePosition: String,
                     @SerializedName("model_engine_cc") val cc: Int,
                     @SerializedName("model_engine_cyl") val cylinders: Int,
                     @SerializedName("model_engine_type") val engineType: String,
                     @SerializedName("model_engine_valves_per_cyl") val valvesPerCylinder: Int,
                     @SerializedName("model_engine_power_ps") val enginePowerPs: Int,
                     @SerializedName("model_engine_power_rpm") val enginePowerRpm: Int,
                     @SerializedName("model_engine_torque_nm") val engineTorqueNm: Int,
                     @SerializedName("model_engine_torque_rpm") val engineTorqueRpm: Int,
                     @SerializedName("model_engine_bore_mm") val engineBoreMm: Float,
                     @SerializedName("model_engine_stroke_mm") val engineStrokeMm: Float,
                     @SerializedName("model_engine_compression") val engineCompression: String,
                     @SerializedName("model_engine_fuel") val fuelType: String,
                     @SerializedName("model_top_speed_kph") val topSpeedKmPh: Int,
                     @SerializedName("model_0_to_100_kph") val seconds0To100: Float,
                     @SerializedName("model_drive") val drive: String,
                     @SerializedName("model_transmission_type") val transmissionType: String,
                     @SerializedName("model_seats") val seats: Int,
                     @SerializedName("model_doors") val doors: Int,
                     @SerializedName("model_weight_kg") val weightInKg: Int,
                     @SerializedName("model_length_mm") val lengthInMm: Int,
                     @SerializedName("model_width_mm") val widthInMm: Int,
                     @SerializedName("model_height_mm") val heightInMm: Int,
                     @SerializedName("model_wheelbase_mm") val wheelbaseInMm: Int,
                     @SerializedName("model_lkm_hwy") val litresPer100KmMotorway: Int,
                     @SerializedName("model_lkm_city") val litresPer100KmCity: Int,
                     @SerializedName("model_fuel_cap_l") val fuelCapacityLitres: Int,
                     @SerializedName("model_sold_in_us") val soldInUsa: Int,
                     @SerializedName("model_co2") val co2: String,
                     @SerializedName("model_make_display") val manufacturerName: String,
                     @SerializedName("make_display") val makeDisplay: String,
                     @SerializedName("make_country") val country: String)

}
