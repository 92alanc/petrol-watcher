package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkAuthentication()
    }

    private fun checkAuthentication() {
        if (AuthenticationManager.USER != null) {
            // TODO: open home activity
        }
    }

}
