package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.model.VehicleType
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun fuelSetToStringFloatMap(input: MutableSet<Fuel>): Map<String, Float> {
    val output = HashMap<String, Float>()

    for (entry in input) {
        val type = entry.type.name
        val quality = entry.quality.name
        val key = "$type,$quality"
        val value = entry.price
        output[key] = value
    }

    return output
}

fun stringFloatMapToFuelList(input: Map<String, Float>): ArrayList<Fuel> {
    val output = ArrayList<Fuel>()

    for (entry in input) {
        val typeString = entry.key.split(",")[0]
        val qualityString = entry.key.split(",")[1]

        val type = stringToFuelType(typeString)
        val quality = stringToFuelQuality(qualityString)
        val price = entry.value

        output.add(Fuel(type, quality, price))
    }

    return output
}

fun Context.fuelQualityToString(fuelQuality: Fuel.Quality): String {
    return when (fuelQuality) {
        Fuel.Quality.PREMIUM -> getString(R.string.premium)
        Fuel.Quality.REGULAR -> getString(R.string.regular)
    }
}

fun stringToFuelQuality(string: String): Fuel.Quality {
    return when (string) {
        "REGULAR" -> Fuel.Quality.REGULAR
        "PREMIUM" -> Fuel.Quality.PREMIUM
        else -> Fuel.Quality.REGULAR
    }
}

fun Context.fuelTypeToString(fuelType: Fuel.Type): String {
    return when (fuelType) {
        Fuel.Type.DIESEL -> getString(R.string.diesel)
        Fuel.Type.ETHANOL -> getString(R.string.ethanol)
        Fuel.Type.LPG -> getString(R.string.lpg)
        Fuel.Type.PETROL -> getString(R.string.petrol)
    }
}

fun stringToFuelType(string: String): Fuel.Type {
    return when (string) {
        "LPG" -> Fuel.Type.LPG
        "DIESEL" -> Fuel.Type.DIESEL
        "ETHANOL" -> Fuel.Type.ETHANOL
        "PETROL" -> Fuel.Type.PETROL
        else -> Fuel.Type.PETROL
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

fun Context.fuelTypeListToString(fuelTypes: ArrayList<Fuel.Type>): String {
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

fun floatToCurrencyString(value: Float): String {
    val currency = Currency.getInstance(Locale.getDefault()).symbol
    return "$currency $value"
}

fun kilometresToMiles(kilometres: Float) = kilometres * 0.621371f

fun milesToKilometres(miles: Float) = miles * 1.60934f