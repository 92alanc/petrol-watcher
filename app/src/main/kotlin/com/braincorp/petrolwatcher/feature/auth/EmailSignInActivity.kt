package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import kotlinx.android.synthetic.main.activity_email_sign_in.*

class EmailSignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_in)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}