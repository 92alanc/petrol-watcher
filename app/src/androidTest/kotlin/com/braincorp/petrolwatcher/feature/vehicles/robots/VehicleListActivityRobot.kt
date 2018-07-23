package com.braincorp.petrolwatcher.feature.vehicles.robots

import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.braincorp.petrolwatcher.R

fun vehicleList(func: VehicleListActivityRobot.() -> Unit) = VehicleListActivityRobot().apply(func)

class VehicleListActivityRobot {

    fun numberOfItemsIs(numberOfItems: Int) {
        recyclerView(R.id.recycler_view) {
            sizeIs(numberOfItems)
        }
    }

    private fun applyResult(func: VehicleListResult.() -> Unit) {
        VehicleListResult().apply(func)
    }

}

class VehicleListResult {

    // TODO: implement

}