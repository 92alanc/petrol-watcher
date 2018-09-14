package com.braincorp.petrolwatcher.feature.stations.utils

import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.util.*

/**
 * Checks if the new fuel is already present in the set.
 * If positive, the price in the set will be updated,
 * otherwise the new fuel will be added to the set
 *
 * @param fuelSet the set
 * @param newFuel the new fuel
 */
fun updateFuelSet(fuelSet: MutableSet<Fuel>, newFuel: Fuel) {
    val isDuplicate = fuelSet.any {
        newFuel.type == it.type && newFuel.quality == it.quality
    }

    if (isDuplicate) {
        fuelSet.first {
            newFuel.type == it.type && newFuel.quality == it.quality
        }.price = newFuel.price
    } else {
        fuelSet.add(newFuel)
    }
}

/**
 * Formats a price in the local currency
 *
 * @param price the price
 * @param locale the locale
 *
 * @return the price formatted in the local currency
 */
fun formatPriceAsCurrency(price: BigDecimal, locale: Locale): String {
    val currency = Currency.getInstance(locale).symbol
    val roundedPrice = price.round(MathContext(3)) // precision = 3
    val formatter = DecimalFormat.getNumberInstance(locale)
    with(formatter) {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    val formattedPrice = formatter.format(roundedPrice.toDouble())
    return "$currency $formattedPrice"
}
