package com.braincorp.petrolwatcher.feature.vehicles.robots

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.clearText
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.VehicleListActivity

fun VehicleDetailsActivityTest.vehicleDetails(func: VehicleDetailsActivityRobot.() -> Unit): VehicleDetailsActivityRobot {
    return VehicleDetailsActivityRobot(rule).apply(func)
}

class VehicleDetailsActivityRobot(private val rule: ActivityTestRule<VehicleDetailsActivity>) {

    fun readOnlyFieldsAreDisplayed() {
        VehicleDetailsResult().readOnlyFieldsAreDisplayed()
    }

    fun calculatedDataAreDisplayed() {
        VehicleDetailsResult().calculatedDataAreDisplayed()
    }

    fun clickFab() {
        click {
            id(R.id.fab)
        }
    }

    fun typeYear(year: Int) {
        clearText {
            id(R.id.edt_year)
        }

        typeText(year.toString()) {
            id(R.id.edt_year)
        }
    }

    fun typeManufacturer(manufacturer: String) {
        clearText {
            id(R.id.edt_manufacturer)
        }

        typeText(manufacturer) {
            id(R.id.edt_manufacturer)
        }
    }

    fun typeModel(model: String) {
        clearText {
            id(R.id.edt_model)
        }

        typeText(model) {
            id(R.id.edt_model)
        }
    }

    fun typeTrimLevel(trimLevel: String) {
        clearText {
            id(R.id.edt_trim_level)
        }

        typeText(trimLevel) {
            id(R.id.edt_trim_level)
        }
    }

    fun typeFuelCapacity(capacity: Int) {
        clearText {
            id(R.id.edt_capacity)
        }

        typeText(capacity.toString()) {
            id(R.id.edt_capacity)
        }
    }

    fun typeAvgConsumptionCity(avgConsumptionCity: Float) {
        clearText {
            id(R.id.edt_avg_consumption_city)
        }

        typeText(avgConsumptionCity.toString()) {
            id(R.id.edt_avg_consumption_city)
        }
    }

    fun typeAvgConsumptionMotorway(avgConsumptionMotorway: Float) {
        clearText {
            id(R.id.edt_avg_consumption_motorway)
        }

        typeText(avgConsumptionMotorway.toString()) {
            id(R.id.edt_avg_consumption_motorway)
        }
    }

    infix fun clickFab(func: VehicleDetailsResult.() -> Unit) {
        clickFab()
        applyResult(func)
    }

    infix fun rotateDevice(func: VehicleDetailsResult.() -> Unit) {
        rule.activity.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
        applyResult(func)
    }

    infix fun clickCalculate(func: VehicleDetailsResult.() -> Unit) {
        click {
            id(R.id.bt_calculate)
        }

        applyResult(func)
    }

    private fun applyResult(func: VehicleDetailsResult.() -> Unit) {
        VehicleDetailsResult().apply(func)
    }

}

class VehicleDetailsResult {

    private val context = getTargetContext()

    fun readOnlyFieldsAreDisplayed() {
        displayed {
            id(R.id.label_factory_data)
            id(R.id.txt_name)
            id(R.id.txt_year)
            id(R.id.txt_fuel_capacity)
            id(R.id.txt_avg_consumption_city)
            id(R.id.txt_avg_consumption_motorway)
        }
    }

    fun calculatedDataAreDisplayed() {
        displayed {
            id(R.id.txt_calculated_avg_consumption_city)
            id(R.id.txt_calculated_avg_consumption_motorway)
            id(R.id.bt_calculate)
        }
    }

    fun editableFieldsAreDisplayed() {
        displayed {
            id(R.id.edt_year)
            id(R.id.edt_manufacturer)
            id(R.id.edt_model)
            id(R.id.edt_trim_level)
            id(R.id.edt_capacity)
            id(R.id.edt_avg_consumption_city)
        }
    }

    fun showDialogue() {
        displayed {
            text(R.string.invalid_vehicle_data)
        }
    }

    fun nameIs(name: String) {
        displayed {
            text(name)
        }
    }

    fun yearInEditModeIs(year: Int) {
        displayed {
            text(year.toString())
        }
    }

    fun yearInViewModeIs(year: Int) {
        displayed {
            val text = context.getString(R.string.year_format, year)
            text(text)
        }
    }

    fun manufacturerIs(manufacturer: String) {
        displayed {
            text(manufacturer)
        }
    }

    fun modelIs(model: String) {
        displayed {
            text(model)
        }
    }

    fun trimLevelIs(trimLevel: String) {
        displayed {
            text(trimLevel)
        }
    }

    fun fuelCapacityInEditModeIs(capacity: Int) {
        displayed {
            text(capacity.toString())
        }
    }

    fun fuelCapacityInViewModeIs(capacity: Int) {
        displayed {
            val text = context.getString(R.string.capacity_format, capacity.toString())
            text(text)
        }
    }

    fun avgConsumptionCityInEditModeIs(avgConsumptionCity: Float) {
        displayed {
            text(avgConsumptionCity.toString())
        }
    }

    fun avgConsumptionCityInViewModeIs(avgConsumptionCity: Float) {
        displayed {
            val text = context.getString(R.string.avg_consumption_city_format,
                    avgConsumptionCity.toString())
            text(text)
        }
    }

    fun avgConsumptionMotorwayInEditModeIs(avgConsumptionMotorway: Float) {
        displayed {
            text(avgConsumptionMotorway.toString())
        }
    }

    fun avgConsumptionMotorwayInViewModeIs(avgConsumptionMotorway: Float) {
        displayed {
            val text = context.getString(R.string.avg_consumption_motorway_format,
                    avgConsumptionMotorway.toString())
            text(text)
        }
    }

    fun calculatedAvgConsumptionCityIs(calculatedAvgConsumptionCity: Float) {
        displayed {
            allOf {
                id(R.id.txt_calculated_avg_consumption_city)
                val text = context.getString(R.string.avg_consumption_city_format,
                        calculatedAvgConsumptionCity.toString())
                text(text)
            }
        }
    }

    fun calculatedAvgConsumptionMotorwayIs(calculatedAvgConsumptionMotorway: Float) {
        displayed {
            allOf {
                id(R.id.txt_calculated_avg_consumption_motorway)
                val text = context.getString(R.string.avg_consumption_motorway_format,
                        calculatedAvgConsumptionMotorway.toString())
                text(text)
            }
        }
    }

    fun redirectToVehicleListActivity() {
        sentIntent {
            className(VehicleListActivity::class.java.name)
        }
    }

    fun redirectToConsumptionActivity() {
        // TODO: implement
    }

}