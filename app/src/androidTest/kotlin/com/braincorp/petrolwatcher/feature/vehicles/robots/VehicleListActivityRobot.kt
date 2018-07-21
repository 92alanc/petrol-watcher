package com.braincorp.petrolwatcher.feature.vehicles.robots

fun vehicleList(func: VehicleListActivityRobot.() -> Unit) = VehicleListActivityRobot().apply(func)

class VehicleListActivityRobot {

    // TODO: implement

    private fun applyResult(func: VehicleListResult.() -> Unit) {
        VehicleListResult().apply(func)
    }

}

class VehicleListResult {

    // TODO: implement

}