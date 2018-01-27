package com.braincorp.petrolwatcher.utils

import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.model.FuelType
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.model.VehicleType
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EnumConverterTest {

    private val context = getTargetContext()

    @Test
    fun shouldConvertPremiumFuelQualityToString() {
        val expected = context.getString(R.string.premium)
        val actual = context.fuelQualityToString(FuelQuality.PREMIUM)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertRegularFuelQualityToString() {
        val expected = context.getString(R.string.regular)
        val actual = context.fuelQualityToString(FuelQuality.REGULAR)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertAutogasFuelTypeToString() {
        val expected = context.getString(R.string.autogas)
        val actual = context.fuelTypeToString(FuelType.AUTOGAS)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertDieselFuelTypeToString() {
        val expected = context.getString(R.string.diesel)
        val actual = context.fuelTypeToString(FuelType.DIESEL)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertEthanolFuelTypeToString() {
        val expected = context.getString(R.string.ethanol)
        val actual = context.fuelTypeToString(FuelType.ETHANOL)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertPetrolFuelTypeToString() {
        val expected = context.getString(R.string.petrol)
        val actual = context.fuelTypeToString(FuelType.PETROL)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertVeryBadRatingToString() {
        val expected = context.getString(R.string.very_bad)
        val actual = context.ratingToString(Rating.VERY_BAD)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertBadRatingToString() {
        val expected = context.getString(R.string.bad)
        val actual = context.ratingToString(Rating.BAD)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertOkRatingToString() {
        val expected = context.getString(R.string.rating_ok)
        val actual = context.ratingToString(Rating.OK)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertGoodRatingToString() {
        val expected = context.getString(R.string.good)
        val actual = context.ratingToString(Rating.GOOD)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertVeryGoodRatingToString() {
        val expected = context.getString(R.string.very_good)
        val actual = context.ratingToString(Rating.VERY_GOOD)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertCarVehicleTypeToString() {
        val expected = context.getString(R.string.car)
        val actual = context.vehicleTypeToString(VehicleType.CAR)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertLorryVehicleTypeToString() {
        val expected = context.getString(R.string.lorry)
        val actual = context.vehicleTypeToString(VehicleType.LORRY)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMotorcycleVehicleTypeToString() {
        val expected = context.getString(R.string.motorcycle)
        val actual = context.vehicleTypeToString(VehicleType.MOTORCYCLE)
        assertEquals(expected, actual)
    }

}