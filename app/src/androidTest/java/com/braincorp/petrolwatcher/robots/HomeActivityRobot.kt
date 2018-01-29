package com.braincorp.petrolwatcher.robots

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
import com.braincorp.petrolwatcher.activities.HomeActivity
import com.braincorp.petrolwatcher.activities.ProfileActivity
import org.junit.Rule

class HomeActivityRobot : BaseRobot() {

    @Rule
    private val rule = ActivityTestRule<HomeActivity>(HomeActivity::class.java,
            false, false)

    fun launchActivity(): HomeActivityRobot {
        rule.launchActivity(Intent())
        return this
    }

    fun openNavigationBar(): HomeActivityRobot {
        onView(withId(R.id.drawer_home)).perform(open())
        return this
    }

    fun clickOnSignOut(): HomeActivityRobot {
        onView(withId(R.id.navigationView)).perform(navigateTo(R.id.itemSignOut))
        return this
    }

    fun clickOnYesDialogueButton(): HomeActivityRobot {
        onView(withText(R.string.yes)).perform(click())
        return this
    }

    fun clickOnProfile(): HomeActivityRobot {
        onView(withText(R.string.profile)).perform(click())
        return this
    }

    fun checkIfLaunchesLoginActivity(): HomeActivityRobot {
        intended(hasComponent(LoginActivityRobot::class.java.name))
        return this
    }

    fun checkIfNavigationBarIsOpen(): HomeActivityRobot {
        onView(withId(R.id.drawer_home)).check(matches(isOpen()))
        return this
    }

    fun checkIfShowsQuestionDialogue(): HomeActivityRobot {
        onView(withText(R.string.question_sign_out)).check(matches(isDisplayed()))
        return this
    }

    fun checkIfLaunchesProfileActivity(): HomeActivityRobot {
        intended(hasComponent(ProfileActivity::class.java.name))
        return this
    }

}