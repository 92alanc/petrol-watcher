package com.braincorp.petrolwatcher.utils

import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.roundToInt

/**
 * Converts an int array to a range
 *
 * @return the range
 */
fun IntArray.toRange(): IntRange {
    this.sort()
    return IntRange(this[0], this[size - 1])
}

/**
 * Converts l/100km to km/l
 *
 * @param l100Km the value in l/100km
 *
 * @return the converted value in km/l, rounded
 *         to 1 decimal place
 */
fun l100KmToKmL(l100Km: Float): Float {
    val convertedValueStr = (100f / l100Km).toString()
    return BigDecimal(convertedValueStr).round(MathContext(3)).toFloat()
}

/**
 * Converts a tank state to the actual quantity
 * in litres
 *
 * @param tankState the tank state (fraction)
 * @param fuelCapacity the fuel capacity
 *
 * @return the quantity in litres, relative to the
 *         the fuel capacity
 */
fun tankStateToLitres(tankState: TankState, fuelCapacity: Int): Int {
    return when (tankState) {
        TankState.FULL -> fuelCapacity
        TankState.THREE_QUARTERS -> (fuelCapacity * 0.75).roundToInt()
        TankState.HALF -> fuelCapacity / 2
        TankState.QUARTER -> (fuelCapacity * 0.25).roundToInt()
    }
}
