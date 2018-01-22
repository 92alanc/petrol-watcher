package com.braincorp.petrolwatcher.robots.assertion

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.LoginActivity

fun checkIfLaunchesLoginActivity() {
    intended(hasComponent(LoginActivity::class.java.name))
}

fun checkIfShowsQuestionDialogue() {
    onView(withText(R.string.question_sign_out)).check(matches(isDisplayed()))
}