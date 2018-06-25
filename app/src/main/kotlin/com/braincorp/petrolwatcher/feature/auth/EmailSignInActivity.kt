package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.controller.EmailSignInActivityController
import com.braincorp.petrolwatcher.utils.startNewAccountActivity
import kotlinx.android.synthetic.main.activity_email_sign_in.*
import kotlinx.android.synthetic.main.content_email_sign_in.*

class EmailSignInActivity : AppCompatActivity(), View.OnClickListener {

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
            R.id.bt_sign_up -> startNewAccountActivity()
        }
    }

    private fun setupButtons() {
        bt_sign_in.setOnClickListener(this)
        bt_sign_up.setOnClickListener(this)
    }

}