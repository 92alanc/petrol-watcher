package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.EmailAndPasswordSignUpActivity
import com.braincorp.petrolwatcher.feature.auth.EmailSignInActivity
import com.braincorp.petrolwatcher.feature.auth.MainActivity

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

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}