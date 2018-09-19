package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.*
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.braincorp.petrolwatcher.feature.consumption.ConsumptionActivity
import com.braincorp.petrolwatcher.feature.stations.*
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.vehicles.CreateVehicleActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleDetailsActivity
import com.braincorp.petrolwatcher.feature.vehicles.VehicleListActivity
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.google.android.gms.maps.model.LatLng

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
 * @param editMode if true, the activity will start
 *                 in edit mode
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startProfileActivity(editMode: Boolean = false,
                                           finishCurrent: Boolean = false) {
    val intent = ProfileActivity.intent(this, editMode)
    startActivity(intent)
    if (finishCurrent)
        finish()
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
 * @param vehicle the vehicle whose fuel consumption
 *                will be calculated
 */
fun AppCompatActivity.startConsumptionActivity(vehicle: Vehicle,
                                               requestCode: Int) {
    val intent = ConsumptionActivity.intent(this, vehicle)
    startActivityForResult(intent, requestCode)
}

/**
 * Starts the create petrol station activity
 *
 * @param finishCurrent if true, the current
 *                      activity will be finished
 * @param currentLocation the current location
 */
fun AppCompatActivity.startCreatePetrolStationActivity(finishCurrent: Boolean = false,
                                                       currentLocation: LatLng?) {
    val intent = CreatePetrolStationActivity.intent(this, currentLocation)
    startActivity(intent)
    if (finishCurrent)
        finish()
}

/**
 * Starts the petrol station list activity
 *
 * @param currentLocation the current location
 * @param finishCurrent if true, the current
 *                      activity will be finished
 */
fun AppCompatActivity.startPetrolStationListActivity(currentLocation: LatLng?,
                                                     finishCurrent: Boolean = false) {
    val intent = PetrolStationListActivity.intent(this, currentLocation)
    startActivity(intent)
    if (finishCurrent)
        finish()
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
    val intent = PetrolStationDetailsActivity.intent(this, petrolStation)
    startActivity(intent)
    if (finishCurrent)
        finish()
}

/**
 * Starts the fuel activity
 *
 * @param fuel the fuel, in case of edit mode
 * @param requestCode the request code
 */
fun AppCompatActivity.startFuelActivity(fuel: Fuel? = null, requestCode: Int) {
    val intent = FuelActivity.intent(this, fuel)
    startActivityForResult(intent, requestCode)
}

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}