package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Intent
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailsActivityTest : BaseActivityTest<VehicleDetailsActivity>(
        VehicleDetailsActivity::class.java) {

    override fun intent(): Intent {
        val intent = super.intent()
        val vehicle = Vehicle(manufacturer = "Ford",
                model = "Focus",
                year = 2015,
                details = Vehicle.Details(trimLevel = "1.4",
                        fuelCapacity = 50,
                        avgConsumptionCity = 10.2f,
                        avgConsumptionMotorway = 14.5f),
                calculatedValues = Vehicle.CalculatedValues(avgConsumptionCity = 9.8f,
                        avgConsumptionMotorway = 13.2f))
        intent.putExtra("vehicle", vehicle)
        return intent
    }

    @Test
    fun shouldStartDisplayingReadOnlyFields() {
        vehicleDetails {
            readOnlyFieldsAreDisplayed()
        }
    }

    @Test
    fun shouldStartDisplayingCalculatedData() {
        vehicleDetails {
            calculatedDataAreDisplayed()
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldDisplayEditableFields() {
        vehicleDetails {
        } clickFab {
            editableFieldsAreDisplayed()
        }
    }

}