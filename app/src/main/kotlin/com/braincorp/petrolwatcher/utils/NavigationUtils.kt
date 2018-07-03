package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.EmailAndPasswordSignUpActivity
import com.braincorp.petrolwatcher.feature.auth.EmailSignInActivity
import com.braincorp.petrolwatcher.feature.auth.MainActivity

fun AppCompatActivity.startMainActivity(finishCurrent: Boolean = false) {
    startActivity(MainActivity::class.java, finishCurrent)
}

fun AppCompatActivity.startEmailSignInActivity(finishCurrent: Boolean = false) {
    startActivity(EmailSignInActivity::class.java, finishCurrent)
}

fun AppCompatActivity.startEmailAndPasswordSignUpActivity(finishCurrent: Boolean = false) {
    startActivity(EmailAndPasswordSignUpActivity::class.java, finishCurrent)
}

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}