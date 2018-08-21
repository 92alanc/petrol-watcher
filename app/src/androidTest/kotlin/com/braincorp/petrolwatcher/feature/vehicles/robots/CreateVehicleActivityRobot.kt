package com.braincorp.petrolwatcher.feature.vehicles.robots

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withSpinnerText
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.menu.menu
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.CreateVehicleActivity
import com.braincorp.petrolwatcher.feature.vehicles.CreateVehicleActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import org.hamcrest.CoreMatchers.*

fun CreateVehicleActivityTest.createVehicle(func: CreateVehicleActivityRobot.() -> Unit):
        CreateVehicleActivityRobot {
    return CreateVehicleActivityRobot(rule).apply(func)
}

class CreateVehicleActivityRobot(private val rule: ActivityTestRule<CreateVehicleActivity>) {

    private var yearPosition = 0
    private var manufacturerPosition = 0
    private var modelPosition = 0
    private var detailsPosition = 0

    fun autoInputViewsAreVisible() {
        CreateVehicleResult().autoInputViewsAreVisible()
    }

    fun clickManualInputMenuItem() {
        menu {
            onItem(R.string.manual_input) {
                click()
            }
        }
    }

    fun yearPositionIs(position: Int) {
        yearPosition = position
    }

    fun manufacturerPositionIs(position: Int) {
        manufacturerPosition = position
    }

    fun modelPositionIs(position: Int) {
        modelPosition = position
    }

    fun detailsPositionIs(position: Int) {
        detailsPosition = position
    }

    fun selectYear() {
        click {
            id(R.id.spn_year)
        }

        onData(allOf(`is`(instanceOf(Int::class.java))))
                .atPosition(yearPosition)
                .perform(ViewActions.click())
    }

    fun selectManufacturer() {
        click {
            id(R.id.spn_manufacturer)
        }

        onData(allOf(`is`(instanceOf(String::class.java))))
                .atPosition(manufacturerPosition)
                .perform(ViewActions.click())
    }

    fun selectModel() {
        click {
            id(R.id.spn_model)
        }

        onData(allOf(`is`(instanceOf(String::class.java))))
                .atPosition(modelPosition)
                .perform(ViewActions.click())
    }

    fun selectDetails() {
        click {
            id(R.id.spn_details)
        }

        onData(allOf(`is`(instanceOf(Vehicle.Details::class.java))))
                .atPosition(detailsPosition)
                .perform(ViewActions.click())
    }

    fun typeYear(year: Int) {
        typeText(year.toString()) {
            id(R.id.edt_year)
        }
    }

    fun typeManufacturer(manufacturer: String) {
        typeText(manufacturer) {
            id(R.id.edt_manufacturer)
        }
    }

    fun typeModel(model: String) {
        typeText(model) {
            id(R.id.edt_model)
        }
    }

    fun typeTrimLevel(trimLevel: String) {
        typeText(trimLevel) {
            id(R.id.edt_trim_level)
        }
    }

    fun typeFuelCapacity(capacity: Int) {
        typeText(capacity.toString()) {
            id(R.id.edt_capacity)
        }
    }

    fun typeAvgConsumptionCity(avgConsumptionCity: Float) {
        typeText(avgConsumptionCity.toString()) {
            id(R.id.edt_avg_consumption_city)
        }
    }

    fun typeAvgConsumptionMotorway(avgConsumptionMotorway: Float) {
        typeText(avgConsumptionMotorway.toString()) {
            id(R.id.edt_avg_consumption_motorway)
        }
    }

    infix fun selectYear(func: CreateVehicleResult.() -> Unit) {
        selectYear()
        applyResult(func)
    }

    infix fun selectManufacturer(func: CreateVehicleResult.() -> Unit) {
        selectManufacturer()
        applyResult(func)
    }

    infix fun selectModel(func: CreateVehicleResult.() -> Unit) {
        selectModel()
        applyResult(func)
    }

    infix fun selectDetails(func: CreateVehicleResult.() -> Unit) {
        selectDetails()
        applyResult(func)
    }

    infix fun clickManualInputMenuItem(func: CreateVehicleResult.() -> Unit) {
        clickManualInputMenuItem()
        applyResult(func)
    }

    infix fun clickAutoInputMenuItem(func: CreateVehicleResult.() -> Unit) {
        menu {
            onItem(R.string.automatic_input) {
                click()
            }
        }

        applyResult(func)
    }

    infix fun rotateDevice(func: CreateVehicleResult.() -> Unit) {
        rule.activity.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
        applyResult(func)
    }

    private fun applyResult(func: CreateVehicleResult.() -> Unit) {
        CreateVehicleResult().apply(func)
    }

}

class CreateVehicleResult {

    fun autoInputViewsAreVisible() {
        displayed {
            id(R.id.label_spn_year)
            id(R.id.spn_year)
            id(R.id.label_spn_manufacturer)
            id(R.id.spn_manufacturer)
            id(R.id.label_spn_model)
            id(R.id.spn_model)
            id(R.id.label_spn_details)
            id(R.id.spn_details)
        }
    }

    fun manualInputViewsAreVisible() {
        displayed {
            id(R.id.edt_year)
            id(R.id.edt_manufacturer)
            id(R.id.edt_model)
            id(R.id.edt_trim_level)
            id(R.id.edt_capacity)
            id(R.id.edt_avg_consumption_city)
            id(R.id.edt_avg_consumption_motorway)
        }
    }

    fun selectedYearIs(year: Int) {
        onView(withId(R.id.spn_year)).check(matches(withSpinnerText(year.toString())))
    }

    fun selectedManufacturerIs(manufacturer: String) {
        onView(withId(R.id.spn_manufacturer)).check(matches(withSpinnerText(manufacturer)))
    }

    fun selectedModelIs(model: String) {
        onView(withId(R.id.spn_model)).check(matches(withSpinnerText(model)))
    }

    fun selectedDetailsIs(details: String) {
        onView(withId(R.id.spn_details)).check(matches(withSpinnerText(containsString(details))))
    }

    fun yearIs(year: Int) {
        displayed {
            allOf {
                id(R.id.edt_year)
                text(year.toString())
            }
        }
    }

    fun manufacturerIs(manufacturer: String) {
        displayed {
            allOf {
                id(R.id.edt_manufacturer)
                text(manufacturer)
            }
        }
    }

    fun modelIs(model: String) {
        displayed {
            allOf {
                id(R.id.edt_model)
                text(model)
            }
        }
    }

    fun trimLevelIs(trimLevel: String) {
        displayed {
            allOf {
                id(R.id.edt_trim_level)
                text(trimLevel)
            }
        }
    }

    fun fuelCapacityIs(capacity: Int) {
        displayed {
            allOf {
                id(R.id.edt_capacity)
                text(capacity.toString())
            }
        }
    }

    fun avgConsumptionCityIs(avgConsumptionCity: Float) {
        displayed {
            allOf {
                id(R.id.edt_avg_consumption_city)
                text(avgConsumptionCity.toString())
            }
        }
    }

    fun avgConsumptionMotorwayIs(avgConsumptionMotorway: Float) {
        displayed {
            allOf {
                id(R.id.edt_avg_consumption_motorway)
                text(avgConsumptionMotorway.toString())
            }
        }
    }

}