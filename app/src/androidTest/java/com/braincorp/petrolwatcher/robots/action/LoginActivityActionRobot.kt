package com.braincorp.petrolwatcher.robots.action

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.LoginActivity
import org.junit.Rule

class LoginActivityActionRobot : BaseActionRobot() {

    private companion object {
        const val EMAIL_CORRECT = "alcam.ukdev@gmail.com"
        const val PASSWORD_CORRECT = "abcd1234"

        const val EMAIL_INCORRECT = "incorrect@incorrect.com"
        const val PASSWORD_INCORRECT = "incorrect123"
    }

    @Rule
    val rule = IntentsTestRule<LoginActivity>(LoginActivity::class.java,
            false, false)

    fun launchActivity(): LoginActivityActionRobot {
        rule.launchActivity(Intent())
        return this
    }

    fun typeEmail(correct: Boolean): LoginActivityActionRobot {
        val text = if (correct) EMAIL_CORRECT else EMAIL_INCORRECT
        onView(withId(R.id.editTextEmail)).perform(typeText(text))
        return this
    }

    fun typePassword(correct: Boolean): LoginActivityActionRobot {
        val text = if (correct) PASSWORD_CORRECT else PASSWORD_INCORRECT
        onView(withId(R.id.editTextPassword)).perform(typeText(text))
        return this
    }

    fun clickOnSignIn(): LoginActivityActionRobot {
        onView(withId(R.id.buttonSignIn)).perform(click())
        return this
    }

    fun clickOnSignUp(): LoginActivityActionRobot {
        onView(withId(R.id.buttonSignUp)).perform(click())
        return this
    }

}