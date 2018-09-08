package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.braincorp.petrolwatcher.feature.auth.*
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.braincorp.petrolwatcher.feature.stations.CreatePetrolStationActivity
import com.braincorp.petrolwatcher.feature.stations.MapActivity
import com.braincorp.petrolwatcher.feature.stations.PetrolStationListActivity
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.vehicles.CreateVehicleActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleListActivity
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

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
    startActivity(MapActivity::class.java, finishCurrent)
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
 * Starts the create vehicle activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startCreateVehicleActivity(finishCurrent: Boolean = false) {
    startActivity(CreateVehicleActivity::class.java, finishCurrent)
}

/**
 * Starts the vehicle details activity
 *
 * @param vehicle the vehicle
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startVehicleDetailsActivity(vehicle: Vehicle,
                                                  finishCurrent: Boolean = false) {
    val intent = VehicleDetailsActivity.getIntent(this, vehicle)
    startActivity(intent)
    if (finishCurrent)
        finish()
}

/**
 * Starts the consumption activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startConsumptionActivity(finishCurrent: Boolean = false) {
    // TODO: implement
    Toast.makeText(this, "Consumption activity", LENGTH_SHORT).show()
}

/**
 * Starts the create petrol station activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startCreatePetrolStationActivity(finishCurrent: Boolean = false) {
    startActivity(CreatePetrolStationActivity::class.java, finishCurrent)
}

/**
 * Starts the petrol station list activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startPetrolStationListActivity(finishCurrent: Boolean = false) {
    startActivity(PetrolStationListActivity::class.java, finishCurrent)
}

/**
 * Starts the petrol station details activity
 *
 * @param petrolStation the petrol station
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startPetrolStationDetailsActivity(petrolStation: PetrolStation,
                                                        finishCurrent: Boolean = false) {
    // TODO: implement
    Toast.makeText(this, "Petrol station details activity", LENGTH_SHORT).show()
}

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}