package com.braincorp.petrolwatcher.feature.vehicles.robots

import android.support.annotation.IdRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import com.braincorp.petrolwatcher.R

fun vehicleDetails(func: VehicleDetailsActivityRobot.() -> Unit): VehicleDetailsActivityRobot {
    return VehicleDetailsActivityRobot().apply(func)
}

class VehicleDetailsActivityRobot {

    infix fun clickAutoInputMenuItem(func: VehicleDetailsResult.() -> Unit) {
        openMenu()
        click {
            text(R.string.automatic_input)
        }

        applyResult(func)
    }

    infix fun clickManualInputMenuItem(func: VehicleDetailsResult.() -> Unit) {
        openMenu()
        click {
            text(R.string.manual_input)
        }

        applyResult(func)
    }

    private fun openMenu() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
    }

    private fun applyResult(func: VehicleDetailsResult.() -> Unit) {
        VehicleDetailsResult().apply(func)
    }

}

class VehicleDetailsResult {

    fun autoInputViewsAreVisible() {
        checkVisibility(R.id.group_auto_input, ViewMatchers.Visibility.VISIBLE)
    }

    fun manualInputViewsAreVisible() {
        checkVisibility(R.id.group_manual_input, ViewMatchers.Visibility.VISIBLE)
    }

    fun autoInputViewsAreNotVisible() {
        checkVisibility(R.id.group_auto_input, ViewMatchers.Visibility.GONE)
    }

    fun manualInputViewsAreNotVisible() {
        checkVisibility(R.id.group_manual_input, ViewMatchers.Visibility.GONE)
    }

    private fun checkVisibility(@IdRes viewId: Int, visibility: ViewMatchers.Visibility) {
        onView(withId(viewId)).check(matches(withEffectiveVisibility(visibility)))
    }

}