package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Intent
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import com.braincorp.petrolwatcher.ui.MultiStateUi
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailsActivityReadOnlyTest : BaseActivityTest<VehicleDetailsActivity>(
        VehicleDetailsActivity::class.java) {

    private val uiState = MultiStateUi.State.READ_ONLY
    private val vehicle = Vehicle(manufacturer = "Volkswagen",
            model = "Golf",
            year = 2012,
            trimLevel = "1.8",
            fuelCapacity = 60,
            avgConsumptionCity = 8f,
            avgConsumptionMotorway = 12f)

    @Test
    fun shouldDisplayReadOnlyFields() {
        vehicleDetails {
            showReadOnlyFields()
        }
    }

    @Test
    fun shouldNotDisplayEditableFields() {
        vehicleDetails {
            hideEditableFields()
        }
    }

    override fun intent(): Intent {
        val intent = super.intent()
        with (intent) {
            putExtra("ui_state", uiState)
            putExtra("vehicle", vehicle)
        }
        return intent
    }

}