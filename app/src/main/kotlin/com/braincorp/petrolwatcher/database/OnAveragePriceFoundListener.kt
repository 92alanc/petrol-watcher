package com.braincorp.petrolwatcher.database

import java.math.BigDecimal

/**
 * Listener for average prices
 */
interface OnAveragePriceFoundListener {
    /**
     * Callback triggered when the average price is found
     *
     * @param averagePrice the average price
     */
    fun onAveragePriceFound(averagePrice: BigDecimal)
}