package com.braincorp.petrolwatcher.robots.action

import android.content.Intent
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.rule.IntentsTestRule
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

    fun clickOnSignOut(): HomeActivityActionRobot {
        openActionBarOverflowOrOptionsMenu(getTargetContext())
        onView(withText(R.string.sign_out)).perform(click())
        return this
    }

    fun clickOnYesDialogueButton(): HomeActivityActionRobot {
        onView(withText(R.string.yes)).perform(click())
        return this
    }

}