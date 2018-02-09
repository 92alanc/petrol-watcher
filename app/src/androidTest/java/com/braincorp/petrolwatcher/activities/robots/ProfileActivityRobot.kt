package com.braincorp.petrolwatcher.activities.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.ProfileActivity
import com.braincorp.petrolwatcher.activities.VehiclesActivity
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.robots.BaseRobot
import org.junit.Rule

class ProfileActivityRobot : BaseRobot() {

    @Rule
    private val rule = IntentsTestRule<ProfileActivity>(ProfileActivity::class.java,
            false, false)

    fun launchActivity(uiMode: AdaptableUi.Mode): ProfileActivityRobot {
        val intent = ProfileActivity.getIntent(context, uiMode)
        rule.launchActivity(intent)
        return this
    }

    fun checkIfHeaderIsVisible(): ProfileActivityRobot {
        checkIfVisible(R.id.textViewProfileHeader)
        return this
    }

    fun checkIfHeaderIsNotVisible(): ProfileActivityRobot {
        checkIfNotVisible(R.id.textViewProfileHeader)
        return this
    }

    fun checkIfHeaderShowsEmailAndPasswordText(): ProfileActivityRobot {
        onView(withText(R.string.header_email_password)).check(matches(isDisplayed()))
        return this
    }

    fun checkIfShowsChangesNotSavedDialogue(): ProfileActivityRobot {
        onView(withText(R.string.question_changes_not_saved)).check(matches(isDisplayed()))
        return this
    }

    fun checkIfVehiclesButtonIsVisible(): ProfileActivityRobot {
        checkIfVisible(R.id.buttonVehicles)
        return this
    }

    fun checkIfVehiclesButtonIsNotVisible(): ProfileActivityRobot {
        checkIfNotVisible(R.id.buttonVehicles)
        return this
    }

    fun clickOnVehiclesButton(): ProfileActivityRobot {
        click(R.id.buttonVehicles)
        return this
    }

    fun checkIfLaunchesVehiclesActivity(): ProfileActivityRobot {
        intended(hasComponent(VehiclesActivity::class.java.name))
        return this
    }

}