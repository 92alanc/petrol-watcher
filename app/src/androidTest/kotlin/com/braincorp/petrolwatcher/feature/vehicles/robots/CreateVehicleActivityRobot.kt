package com.braincorp.petrolwatcher.feature.vehicles.robots

import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.menu.menu
import com.braincorp.petrolwatcher.R

fun createVehicle(func: CreateVehicleActivityRobot.() -> Unit): CreateVehicleActivityRobot {
    return CreateVehicleActivityRobot().apply(func)
}

class CreateVehicleActivityRobot {

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

}