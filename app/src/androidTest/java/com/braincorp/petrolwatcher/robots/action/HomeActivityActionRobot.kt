package com.braincorp.petrolwatcher.robots.action

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.NavigationViewActions.navigateTo
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.HomeActivity
import org.junit.Rule

class HomeActivityActionRobot : BaseActionRobot() {

    @Rule
    private val rule = IntentsTestRule<HomeActivity>(HomeActivity::class.java,
            false, false)

    fun launchActivity(): HomeActivityActionRobot {
        rule.launchActivity(Intent())
        return this
    }

    fun openNavigationBar(): HomeActivityActionRobot {
        onView(withId(R.id.drawer_home)).perform(open())
        return this
    }

    fun clickOnSignOut(): HomeActivityActionRobot {
        onView(withId(R.id.navigationView)).perform(navigateTo(R.id.itemSignOut))
        return this
    }

    fun clickOnYesDialogueButton(): HomeActivityActionRobot {
        onView(withText(R.string.yes)).perform(click())
        return this
    }

}