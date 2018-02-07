package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.model.FuelType
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.model.VehicleType
import java.util.*
import kotlin.collections.HashMap

fun Context.fuelFloatMapToString(map: Map<Pair<FuelType, FuelQuality>, Float>): String {
    val sb = StringBuilder()
    val currencySymbol = Currency.getInstance(Locale.getDefault()).symbol

    for ((i, entry) in map.entries.withIndex()) {
        val type = fuelTypeToString(entry.key.first)
        val quality = fuelQualityToUserFriendlyString(entry.key.second)
        val price = entry.value

        sb.append("$type ($quality): $currencySymbol $price")
        if (i < map.size - 1) sb.append("\n")
    }

    return sb.toString()
}

fun fuelFloatMapToStringFloatMap(input: Map<Pair<FuelType,
        FuelQuality>, Float>): Map<String, Float> {
    val output = HashMap<String, Float>()

    for (entry in input) {
        val type = entry.key.first.name
        val quality = entry.key.second.name
        val key = "$type,$quality"
        val value = entry.value
        output[key] = value
    }

    return output
}

fun stringFloatMapToFuelFloatMap(input: Map<String, Float>)
        : Map<Pair<FuelType, FuelQuality>, Float> {
    val output = HashMap<Pair<FuelType, FuelQuality>, Float>()

    for (entry in input) {
        val typeString = entry.key.split(",")[0]
        val qualityString = entry.key.split(",")[1]

        val type = stringToFuelType(typeString)
        val quality = stringToFuelQuality(qualityString)

        val key = Pair(type, quality)
        val value = entry.value

        output[key] = value
    }

    return output
}

fun Context.fuelQualityToUserFriendlyString(fuelQuality: FuelQuality): String {
    return when (fuelQuality) {
        FuelQuality.PREMIUM -> getString(R.string.premium)
        FuelQuality.REGULAR -> getString(R.string.regular)
    }
}

fun stringToFuelQuality(string: String): FuelQuality {
    return when (string) {
        "REGULAR" -> FuelQuality.REGULAR
        "PREMIUM" -> FuelQuality.PREMIUM
        else -> FuelQuality.REGULAR
    }
}

fun Context.fuelTypeToString(fuelType: FuelType): String {
    return when (fuelType) {
        FuelType.DIESEL -> getString(R.string.diesel)
        FuelType.ETHANOL -> getString(R.string.ethanol)
        FuelType.LPG -> getString(R.string.lpg)
        FuelType.PETROL -> getString(R.string.petrol)
    }
}

fun stringToFuelType(string: String): FuelType {
    return when (string) {
        "LPG" -> FuelType.LPG
        "DIESEL" -> FuelType.DIESEL
        "ETHANOL" -> FuelType.ETHANOL
        "PETROL" -> FuelType.PETROL
        else -> FuelType.PETROL
    }
}

fun stringToRating(string: String): Rating {
    return when (string) {
        "VERY_BAD" -> Rating.VERY_BAD
        "BAD" -> Rating.BAD
        "OK" -> Rating.OK
        "GOOD" -> Rating.GOOD
        "VERY_GOOD" -> Rating.VERY_GOOD
        else -> Rating.OK
    }
}

fun Context.ratingToString(rating: Rating): String {
    return when (rating) {
        Rating.VERY_BAD -> getString(R.string.very_bad)
        Rating.BAD -> getString(R.string.bad)
        Rating.OK -> getString(R.string.rating_ok)
        Rating.GOOD -> getString(R.string.good)
        Rating.VERY_GOOD -> getString(R.string.very_good)
    }
}

fun Context.ratingToColour(rating: Rating): Int {
    val colour = when (rating) {
        Rating.VERY_BAD -> R.color.red_dark
        Rating.BAD -> R.color.red
        Rating.OK -> R.color.amber
        Rating.GOOD -> R.color.green_light
        Rating.VERY_GOOD -> R.color.green
    }
    return ContextCompat.getColor(this, colour)
}

fun Context.vehicleTypeToString(vehicleType: VehicleType): String {
    return when (vehicleType) {
        VehicleType.CAR -> getString(R.string.car)
        VehicleType.LORRY -> getString(R.string.lorry)
        VehicleType.MOTORCYCLE -> getString(R.string.motorcycle)
        VehicleType.VAN -> getString(R.string.van)
    }
}

fun Context.fuelTypeListToString(fuelTypes: ArrayList<FuelType>): String {
    val sb = StringBuilder()
    fuelTypes.map { fuelTypeToString(it) }
            .forEachIndexed { i, fuelTypeString ->
                if (i == fuelTypes.size - 1)
                    sb.append(fuelTypeString)
                else
                    sb.append("$fuelTypeString, ")
            }
    return sb.toString()
}

fun Context.vehicleTypeToDrawable(vehicleType: VehicleType): Drawable {
    val drawableRes = when (vehicleType) {
        VehicleType.CAR -> R.drawable.ic_car
        VehicleType.LORRY -> R.drawable.ic_lorry
        VehicleType.MOTORCYCLE -> R.drawable.ic_motorcycle
        VehicleType.VAN -> R.drawable.ic_van
    }
    return ContextCompat.getDrawable(this, drawableRes)!!
}