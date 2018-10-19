package com.braincorp.petrolwatcher.database

import com.braincorp.petrolwatcher.feature.prediction.model.AveragePrice

/**
 * Listener for average prices
 */
interface OnAveragePriceFoundListener {
    /**
     * Callback triggered when the average price is found
     *
     * @param averagePrice the average price
     */
    fun onAveragePriceFound(averagePrice: AveragePrice)

    /**
     * Callback triggered when the average prices for a city
     * and country are found
     *
     * @param averagePrices the average fuel prices
     */
    fun onAveragePricesFound(averagePrices: ArrayList<AveragePrice>)
}