package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.model.FuelType
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.model.VehicleType

fun Context.fuelQualityToString(fuelQuality: FuelQuality): String {
    return when (fuelQuality) {
        FuelQuality.PREMIUM -> getString(R.string.premium)
        FuelQuality.REGULAR -> getString(R.string.regular)
    }
}

fun Context.fuelTypeToString(fuelType: FuelType): String {
    return when (fuelType) {
        FuelType.AUTOGAS -> getString(R.string.autogas)
        FuelType.DIESEL -> getString(R.string.diesel)
        FuelType.ETHANOL -> getString(R.string.ethanol)
        FuelType.PETROL -> getString(R.string.petrol)
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

fun Context.vehicleTypeToString(vehicleType: VehicleType): String {
    return when (vehicleType) {
        VehicleType.CAR -> getString(R.string.car)
        VehicleType.LORRY -> getString(R.string.lorry)
        VehicleType.MOTORCYCLE -> getString(R.string.motorcycle)
    }
}

fun Context.vehicleTypeToDrawable(vehicleType: VehicleType): Drawable {
    val drawableRes = when (vehicleType) {
        VehicleType.CAR -> R.drawable.ic_car
        VehicleType.LORRY -> R.drawable.ic_lorry
        VehicleType.MOTORCYCLE -> R.drawable.ic_motorcycle
    }
    return ContextCompat.getDrawable(this, drawableRes)!!
}