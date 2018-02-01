package com.braincorp.petrolwatcher.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DistanceConverterTest {

    @Test
    fun shouldConvertKilometresToMiles() {
        val expected = 1.8641129f
        val actual = kilometresToMiles(kilometres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMilesToKilometres() {
        val expected = 4.82802f
        val actual = milesToKilometres(miles = 3f)
        assertEquals(expected, actual)
    }

}