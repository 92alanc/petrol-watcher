package com.braincorp.petrolwatcher.robots.assertion

import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import com.braincorp.petrolwatcher.activities.HomeActivity
import com.braincorp.petrolwatcher.activities.ProfileActivity

fun checkIfLaunchesHomeActivity() {
    intended(IntentMatchers.hasComponent(HomeActivity::class.java.name))
}

fun checkIfLaunchesProfileActivity() {
    intended(IntentMatchers.hasComponent(ProfileActivity::class.java.name))
}