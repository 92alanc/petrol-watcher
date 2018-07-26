package com.braincorp.petrolwatcher.feature.vehicles.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity

fun vehicleList(func: VehicleListActivityRobot.() -> Unit) = VehicleListActivityRobot().apply(func)

class VehicleListActivityRobot {

    fun numberOfItemsIs(numberOfItems: Int) {
        recyclerView(R.id.recycler_view) {
            sizeIs(numberOfItems)
        }
    }

    infix fun clickAddButton(func: VehicleListResult.() -> Unit) {
        click {
            id(R.id.fab)
        }

        applyResult(func)
    }

    private fun applyResult(func: VehicleListResult.() -> Unit) {
        VehicleListResult().apply(func)
    }

}

class VehicleListResult {

    fun redirectToVehicleDetailsActivity() {
        sentIntent {
            className(VehicleDetailsActivity::class.java.name)
        }
    }

}