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
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.notDisplayed
import com.braincorp.petrolwatcher.R

fun vehicleDetails(func: VehicleDetailsActivityRobot.() -> Unit): VehicleDetailsActivityRobot {
    return VehicleDetailsActivityRobot().apply(func)
}

class VehicleDetailsActivityRobot {

    fun showReadOnlyFields() {
        displayed {
            allOf {
                id(R.id.txt_manufacturer)
                id(R.id.txt_model)
                id(R.id.txt_year)
                id(R.id.txt_capacity)
                id(R.id.txt_avg_consumption_city)
                id(R.id.txt_avg_consumption_motorway)
            }
        }
    }

    fun hideEditableFields() {
        notDisplayed {
            allOf {
                // region Auto input
                id(R.id.label_spn_manufacturer)
                id(R.id.spn_manufacturer)
                id(R.id.label_spn_model)
                id(R.id.spn_model)
                id(R.id.label_spn_year)
                id(R.id.spn_year)
                id(R.id.label_spn_trim_level)
                id(R.id.spn_trim_level)
                // endregion

                // region Manual input
                id(R.id.edt_manufacturer)
                id(R.id.edt_model)
                id(R.id.edt_year)
                id(R.id.edt_trim_level)
                id(R.id.edt_capacity)
                id(R.id.edt_avg_consumption_city)
                id(R.id.edt_avg_consumption_motorway)
                // endregion
            }
        }
    }

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

    infix fun clickYearsSpinner(func: VehicleDetailsResult.() -> Unit) {
        click {
            id(R.id.spn_year)
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