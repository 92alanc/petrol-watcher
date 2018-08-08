package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.braincorp.petrolwatcher.feature.auth.*
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleListActivity
import com.braincorp.petrolwatcher.ui.MultiStateUi

/**
 * Starts the main activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startMainActivity(finishCurrent: Boolean = false) {
    startActivity(MainActivity::class.java, finishCurrent)
}

/**
 * Starts the e-mail and password sign in activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startEmailSignInActivity(finishCurrent: Boolean = false) {
    startActivity(EmailSignInActivity::class.java, finishCurrent)
}

/**
 * Starts the e-mail and password sign up activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startEmailAndPasswordSignUpActivity(finishCurrent: Boolean = false) {
    startActivity(EmailAndPasswordSignUpActivity::class.java, finishCurrent)
}

/**
 * Starts the authentication error activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startAuthenticationErrorActivity(errorType: AuthErrorType,
                                                       finishCurrent: Boolean = false) {
    val intent = AuthenticationErrorActivity.getIntent(this, errorType)
    startActivity(intent)
    if (finishCurrent)
        finish()
}

/**
 * Starts the profile activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startProfileActivity(finishCurrent: Boolean = false) {
    startActivity(ProfileActivity::class.java, finishCurrent)
}

/**
 * Starts the map activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startMapActivity(finishCurrent: Boolean = false) {
    // TODO: implement
    Toast.makeText(this, "Map activity", LENGTH_SHORT).show()
}

/**
 * Starts the vehicle list activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startVehicleListActivity(finishCurrent: Boolean = false) {
    startActivity(VehicleListActivity::class.java, finishCurrent)
}

/**
 * Starts the vehicle details activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 * @param uiState the UI state
 */
fun AppCompatActivity.startVehicleDetailsActivity(finishCurrent: Boolean = false,
                                                  uiState: MultiStateUi.State) {
    val intent = VehicleDetailsActivity.getIntent(this, uiState)
    startActivity(intent)
    if (finishCurrent)
        finish()
}

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}