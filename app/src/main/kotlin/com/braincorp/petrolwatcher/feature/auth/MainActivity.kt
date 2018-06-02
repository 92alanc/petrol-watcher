package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.utils.getActiveAccount
import com.braincorp.petrolwatcher.utils.startEmailSignInActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private companion object {
        const val RC_GOOGLE_SIGN_IN = 3892

        const val TAG = "PETROL_WATCHER"
    }

    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build()
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

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Log.w(TAG, "Connection failed! Result -> $result")
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.d(TAG, "result -> ${result.isSuccess}")
        if (result.isSuccess) {
            val account = result.signInAccount
            Toast.makeText(this,
                    "Hello ${account?.displayName}!",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in_google -> signInWithGoogle()
            R.id.bt_sign_in_facebook -> TODO()
            R.id.bt_sign_in_email -> startEmailSignInActivity()
        }
    }

    private fun setupButtons() {
        bt_sign_in_google.setOnClickListener(this)
        bt_sign_in_facebook.setOnClickListener(this)
        bt_sign_in_email.setOnClickListener(this)
    }

    private fun signInWithGoogle() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
    }

}