package com.braincorp.petrolwatcher.activities.robots

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.DrawerMatchers.isOpen
import android.support.test.espresso.contrib.NavigationViewActions.navigateTo
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.MapActivity
import com.braincorp.petrolwatcher.activities.PetrolStationsActivity
import com.braincorp.petrolwatcher.activities.ProfileActivity
import com.braincorp.petrolwatcher.robots.BaseRobot
import org.junit.Rule

class MapActivityRobot : BaseRobot() {

    @Rule
    private val rule = ActivityTestRule<MapActivity>(MapActivity::class.java,
            false, false)

    fun launchActivity(): MapActivityRobot {
        rule.launchActivity(Intent())
        return this
    }

    fun openNavigationBar(): MapActivityRobot {
        onView(withId(R.id.drawer_home)).perform(open())
        return this
    }

    fun clickOnSignOut(): MapActivityRobot {
        onView(withId(R.id.navigationView)).perform(navigateTo(R.id.itemSignOut))
        return this
    }

    fun clickOnYesDialogueButton(): MapActivityRobot {
        onView(withText(R.string.yes)).perform(click())
        return this
    }

    fun clickOnProfile(): MapActivityRobot {
        onView(withText(R.string.profile)).perform(click())
        return this
    }

    fun clickOnStationsNearby(): MapActivityRobot {
        onView(withText(R.string.stations_nearby)).perform(click())
        return this
    }

    fun checkIfLaunchesPetrolStationsActivity(): MapActivityRobot {
        intended(hasComponent(PetrolStationsActivity::class.java.name))
        return this
    }

    fun checkIfLaunchesLoginActivity(): MapActivityRobot {
        intended(hasComponent(LoginActivityRobot::class.java.name))
        return this
    }

    fun checkIfNavigationBarIsOpen(): MapActivityRobot {
        onView(withId(R.id.drawer_home)).check(matches(isOpen()))
        return this
    }

    fun checkIfShowsQuestionDialogue(): MapActivityRobot {
        onView(withText(R.string.question_sign_out)).check(matches(isDisplayed()))
        return this
    }

    fun checkIfLaunchesProfileActivity(): MapActivityRobot {
        intended(hasComponent(ProfileActivity::class.java.name))
        return this
    }

}