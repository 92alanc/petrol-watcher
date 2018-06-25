package com.braincorp.petrolwatcher.utils

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.EmailSignInActivity
import com.braincorp.petrolwatcher.feature.auth.MainActivity
import com.braincorp.petrolwatcher.feature.auth.NewAccountActivity

fun AppCompatActivity.startMainActivity(finishCurrent: Boolean = false) {
    startActivity(MainActivity::class.java, finishCurrent)
}

fun AppCompatActivity.startEmailSignInActivity(finishCurrent: Boolean = false) {
    startActivity(EmailSignInActivity::class.java, finishCurrent)
}

fun AppCompatActivity.startNewAccountActivity(finishCurrent: Boolean = false) {
    startActivity(NewAccountActivity::class.java, finishCurrent)
}

private fun AppCompatActivity.startActivity(destinationClass: Class<*>, finishCurrent: Boolean) {
    val intent = Intent(this, destinationClass)
    startActivity(intent)
    if (finishCurrent)
        finish()
}