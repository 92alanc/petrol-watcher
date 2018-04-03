package com.braincorp.petrolwatcher.utils

import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.petrolstations.model.Fuel
import com.braincorp.petrolwatcher.feature.petrolstations.model.Rating
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversionsTest {

    private val context = getTargetContext()

    @Test
    fun shouldConvertPremiumFuelQualityToString() {
        val expected = context.getString(R.string.premium)
        val actual = context.fuelQualityToString(Fuel.Quality.PREMIUM)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertRegularFuelQualityToString() {
        val expected = context.getString(R.string.regular)
        val actual = context.fuelQualityToString(Fuel.Quality.REGULAR)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertLpgFuelTypeToString() {
        val expected = context.getString(R.string.lpg)
        val actual = context.fuelTypeToString(Fuel.Type.LPG)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertDieselFuelTypeToString() {
        val expected = context.getString(R.string.diesel)
        val actual = context.fuelTypeToString(Fuel.Type.DIESEL)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertEthanolFuelTypeToString() {
        val expected = context.getString(R.string.ethanol)
        val actual = context.fuelTypeToString(Fuel.Type.ETHANOL)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertPetrolFuelTypeToString() {
        val expected = context.getString(R.string.petrol)
        val actual = context.fuelTypeToString(Fuel.Type.PETROL)
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
    fun shouldConvertBadRatingStringToRating() {
        val expected = Rating.BAD
        val actual = stringToRating("BAD")
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertOkRatingStringToRating() {
        val expected = Rating.OK
        val actual = stringToRating("OK")
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertGoodRatingStringToRating() {
        val expected = Rating.GOOD
        val actual = stringToRating("GOOD")
        assertEquals(expected, actual)
    }

}