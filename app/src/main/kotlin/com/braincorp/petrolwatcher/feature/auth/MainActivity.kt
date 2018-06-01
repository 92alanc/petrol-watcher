package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.utils.startEmailSignInActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButtons()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in_google -> TODO()
            R.id.bt_sign_in_facebook -> TODO()
            R.id.bt_sign_in_email -> startEmailSignInActivity()
        }
    }

    private fun setupButtons() {
        bt_sign_in_google.setOnClickListener(this)
        bt_sign_in_facebook.setOnClickListener(this)
        bt_sign_in_email.setOnClickListener(this)
    }

}