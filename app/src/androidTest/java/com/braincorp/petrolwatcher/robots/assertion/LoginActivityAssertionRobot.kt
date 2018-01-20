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