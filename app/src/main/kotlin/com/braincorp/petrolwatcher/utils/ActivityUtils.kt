package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.map.activities.MapActivity
import com.braincorp.petrolwatcher.feature.petrolstations.activities.PetrolStationsActivity
import com.braincorp.petrolwatcher.feature.settings.activities.SettingsActivity
import com.braincorp.petrolwatcher.feature.users.activities.LoginActivity
import com.braincorp.petrolwatcher.feature.users.activities.ProfileActivity
import com.braincorp.petrolwatcher.feature.vehicles.activities.VehiclesActivity
import com.braincorp.petrolwatcher.model.AdaptableUi

fun AppCompatActivity.launchLoginActivity(finishCurrent: Boolean) {
    val intent = LoginActivity.getIntent(this)
    launchActivity(intent, finishCurrent)
}

fun AppCompatActivity.launchMapActivity(finishCurrent: Boolean) {
    val intent = MapActivity.getIntent(this)
    launchActivity(intent, finishCurrent)
}

fun AppCompatActivity.launchProfileActivity(uiMode: AdaptableUi.Mode, finishCurrent: Boolean) {
    val intent = ProfileActivity.getIntent(this, uiMode)
    launchActivity(intent, finishCurrent)
}

fun AppCompatActivity.launchPetrolStationsActivity(finishCurrent: Boolean) {
    val intent = PetrolStationsActivity.getIntent(this)
    launchActivity(intent, finishCurrent)
}

fun AppCompatActivity.launchSettingsActivity(finishCurrent: Boolean) {
    val intent = SettingsActivity.getIntent(this)
    launchActivity(intent, finishCurrent)
}

fun AppCompatActivity.launchVehiclesActivity(pickVehicle: Boolean, finishCurrent: Boolean) {
    val intent = VehiclesActivity.getIntent(this, pickVehicle)
    launchActivity(intent, finishCurrent)
}

private fun AppCompatActivity.launchActivity(intent: Intent, finishCurrent: Boolean) {
    startActivity(intent)
    if (finishCurrent) finish()
}