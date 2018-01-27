package com.braincorp.petrolwatcher.robots

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.HomeActivity
import com.braincorp.petrolwatcher.activities.LoginActivity
import com.braincorp.petrolwatcher.activities.ProfileActivity
import org.junit.Rule

class LoginActivityRobot : BaseRobot() {

    private companion object {
        const val EMAIL_CORRECT = "alcam.ukdev@gmail.com"
        const val PASSWORD_CORRECT = "abcd1234"

        const val EMAIL_INCORRECT = "incorrect@incorrect.com"
        const val PASSWORD_INCORRECT = "incorrect123"
    }

    @Rule
    val rule = IntentsTestRule<LoginActivity>(LoginActivity::class.java,
            false, false)

    fun launchActivity(): LoginActivityRobot {
        rule.launchActivity(Intent())
        return this
    }

    fun typeEmail(correct: Boolean): LoginActivityRobot {
        val text = if (correct) EMAIL_CORRECT else EMAIL_INCORRECT
        onView(withId(R.id.editTextEmail)).perform(typeText(text))
        return this
    }

    fun typePassword(correct: Boolean): LoginActivityRobot {
        val text = if (correct) PASSWORD_CORRECT else PASSWORD_INCORRECT
        onView(withId(R.id.editTextPassword)).perform(typeText(text))
        return this
    }

    fun clickOnSignIn(): LoginActivityRobot {
        onView(withId(R.id.buttonSignIn)).perform(click())
        return this
    }

    fun clickOnSignUp(): LoginActivityRobot {
        onView(withId(R.id.buttonSignUp)).perform(click())
        return this
    }

    fun checkIfLaunchesHomeActivity() {
        intended(hasComponent(HomeActivity::class.java.name))
    }

    fun checkIfLaunchesProfileActivity() {
        intended(hasComponent(ProfileActivity::class.java.name))
    }

    fun checkIfShowsErrorDialogue() {
        onView(withText(R.string.invalid_email_or_password)).check(matches(isDisplayed()))
    }

}