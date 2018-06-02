package com.braincorp.petrolwatcher.feature.auth.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.controller.MainActivityController
import com.braincorp.petrolwatcher.feature.auth.utils.getActiveAccount
import com.braincorp.petrolwatcher.feature.auth.utils.getGoogleApiClient
import com.braincorp.petrolwatcher.feature.auth.utils.signInWithFacebook
import com.braincorp.petrolwatcher.feature.auth.utils.signInWithGoogle
import com.braincorp.petrolwatcher.utils.startEmailSignInActivity
import com.facebook.CallbackManager
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private companion object {
        const val RC_GOOGLE_SIGN_IN = 3892

        const val TAG = "PETROL_WATCHER"
    }

    private val callbackManager = CallbackManager.Factory.create()
    private val controller = MainActivityController()

    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleApiClient = getGoogleApiClient(controller)
        setupButtons()
    }

    override fun onStart() {
        super.onStart()
        if (getActiveAccount() != null) {
            Log.d(TAG, "Already signed in")
            // TODO: start map activity
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN)
            controller.handleGoogleSignInResult(data)

        if (callbackManager.onActivityResult(requestCode, resultCode, data))
            return
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in_google -> signInWithGoogle(googleApiClient, RC_GOOGLE_SIGN_IN)
            R.id.bt_sign_in_facebook -> signInWithFacebook(callbackManager, controller)
            R.id.bt_sign_in_email -> startEmailSignInActivity()
        }
    }

    private fun setupButtons() {
        bt_sign_in_google.setOnClickListener(this)
        bt_sign_in_facebook.setOnClickListener(this)
        bt_sign_in_email.setOnClickListener(this)
    }

}