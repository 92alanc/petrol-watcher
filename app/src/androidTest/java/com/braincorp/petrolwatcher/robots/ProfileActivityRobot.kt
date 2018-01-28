package com.braincorp.petrolwatcher.robots

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.ProfileActivity
import com.braincorp.petrolwatcher.model.UiMode
import org.junit.Rule

class ProfileActivityRobot : BaseRobot() {

    @Rule
    private val rule = ActivityTestRule<ProfileActivity>(ProfileActivity::class.java,
            false, false)

    fun launchActivity(uiMode: UiMode): ProfileActivityRobot {
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

}