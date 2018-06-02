package com.braincorp.petrolwatcher.feature.auth.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.controller.EmailSignInActivityController
import kotlinx.android.synthetic.main.activity_email_sign_in.*
import kotlinx.android.synthetic.main.content_email_sign_in.*

class EmailSignInActivity : AppCompatActivity(), View.OnClickListener {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    private val controller = EmailSignInActivityController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_in)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupButtons()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in -> controller.signIn(edt_email.text.toString(),
                    edt_password.text.toString())
            R.id.bt_sign_up -> Log.d(TAG, "Sign up")
        }
    }

    private fun setupButtons() {
        bt_sign_in.setOnClickListener(this)
        bt_sign_up.setOnClickListener(this)
    }

}