package com.braincorp.petrolwatcher.robots.assertion

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.HomeActivity
import com.braincorp.petrolwatcher.activities.ProfileActivity

fun checkIfLaunchesHomeActivity() {
    intended(IntentMatchers.hasComponent(HomeActivity::class.java.name))
}

fun checkIfLaunchesProfileActivity() {
    intended(IntentMatchers.hasComponent(ProfileActivity::class.java.name))
}

fun checkIfShowsErrorDialogue() {
    onView(withText(R.string.invalid_email_or_password)).check(matches(isDisplayed()))
}

fun checkIfEmailTextIsDisplayed(correct: Boolean) {
    val text = if (correct) "alcam.ukdev@gmail.com" else "incorrect@incorrect.com"
    onView(withText(text)).check(matches(isDisplayed()))
}

fun checkIfPasswordTextIsDisplayed(correct: Boolean) {
    val text = if (correct) "abcd1234" else "incorrect123"
    onView(withText(text)).check(matches(isDisplayed()))
}