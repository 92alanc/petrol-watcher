package com.braincorp.petrolwatcher.feature.vehicles.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.braincorp.petrolwatcher.R

fun vehicleDetails(func: VehicleDetailsActivityRobot.() -> Unit): VehicleDetailsActivityRobot {
    return VehicleDetailsActivityRobot().apply(func)
}

class VehicleDetailsActivityRobot {

    fun readOnlyFieldsAreDisplayed() {
        VehicleDetailsResult().readOnlyFieldsAreDisplayed()
    }

    fun calculatedDataAreDisplayed() {
        VehicleDetailsResult().calculatedDataAreDisplayed()
    }

    infix fun clickFab(func: VehicleDetailsResult.() -> Unit) {
        click {
            id(R.id.fab)
        }

        applyResult(func)
    }

    private fun applyResult(func: VehicleDetailsResult.() -> Unit) {
        VehicleDetailsResult().apply(func)
    }

}

class VehicleDetailsResult {

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

}