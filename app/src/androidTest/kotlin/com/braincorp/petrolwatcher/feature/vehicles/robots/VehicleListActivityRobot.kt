package com.braincorp.petrolwatcher.feature.vehicles.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.withId
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.CreateVehicleActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity
import com.braincorp.petrolwatcher.feature.vehicles.adapter.VehicleAdapter

fun vehicleList(func: VehicleListActivityRobot.() -> Unit) = VehicleListActivityRobot().apply(func)

class VehicleListActivityRobot {

    fun numberOfItemsIs(numberOfItems: Int) {
        VehicleListResult().numberOfItemsIs(numberOfItems)
    }

    infix fun clickAddButton(func: VehicleListResult.() -> Unit) {
        click {
            id(R.id.fab)
        }

        applyResult(func)
    }

    infix fun swipeItem(func: VehicleListResult.() -> Unit) {
        onView(withId(R.id.recycler_view)).perform(
                actionOnItemAtPosition<VehicleAdapter.VehicleHolder>(0, swipeLeft()))

        applyResult(func)
    }

    infix fun clickItem(func: VehicleListResult.() -> Unit) {
        recyclerView(R.id.recycler_view) {
            atPosition(0) {
                click()
            }
        }

        applyResult(func)
    }

    private fun applyResult(func: VehicleListResult.() -> Unit) {
        VehicleListResult().apply(func)
    }

}

class VehicleListResult {

    fun redirectToCreateVehicleActivity() {
        sentIntent {
            className(CreateVehicleActivity::class.java.name)
        }
    }

    fun redirectToVehicleDetailsActivity() {
        sentIntent {
            className(VehicleDetailsActivity::class.java.name)
        }
    }

    fun numberOfItemsIs(numberOfItems: Int) {
        recyclerView(R.id.recycler_view) {
            sizeIs(numberOfItems)
        }
    }

}