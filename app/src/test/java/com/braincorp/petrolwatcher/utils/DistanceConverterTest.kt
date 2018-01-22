package com.braincorp.petrolwatcher.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DistanceConverterTest {

    @Test
    fun shouldConvertKilometresToMetres() {
        val expected = 3000f
        val actual = kilometresToMetres(kilometres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertKilometresToMiles() {
        val expected = 1.8641129f
        val actual = kilometresToMiles(kilometres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertKilometresToYards() {
        val expected = 3280.83f
        val actual = kilometresToYards(kilometres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMetresToMiles() {
        val expected = 0.0018641129f
        val actual = metresToMiles(metres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMetresToKilometres() {
        val expected = 0.003f
        val actual = metresToKilometres(metres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMetresToYards() {
        val expected = 3.2808301f
        val actual = metresToYards(metres = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMilesToKilometres() {
        val expected = 4.82802f
        val actual = milesToKilometres(miles = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMilesToMetres() {
        val expected = 4828.02f
        val actual = milesToMetres(miles = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertMilesToYards() {
        val expected = 5280f
        val actual = milesToYards(miles = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertYardsToKilometres() {
        val expected = 0.0027432f
        val actual = yardsToKilometres(yards = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertYardsToMetres() {
        val expected = 2.7431998f
        val actual = yardsToMetres(yards = 3f)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldConvertYardsToMiles() {
        val expected = 0.0017045459f
        val actual = yardsToMiles(yards = 3f)
        assertEquals(expected, actual)
    }

}