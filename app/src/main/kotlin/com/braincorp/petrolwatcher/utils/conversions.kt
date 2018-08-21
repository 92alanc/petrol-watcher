package com.braincorp.petrolwatcher.utils

import java.math.BigDecimal
import java.math.MathContext

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
