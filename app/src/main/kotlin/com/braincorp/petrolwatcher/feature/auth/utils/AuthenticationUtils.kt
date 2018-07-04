package com.braincorp.petrolwatcher.feature.auth.utils

import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Gets a client for the Google authentication API
 *
 * @param onConnectionFailedListener the listener that will
 *                                   be called in case of failure
 * @return the Google authentication API client
 */
fun AppCompatActivity.getGoogleApiClient(
        onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener): GoogleApiClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    return GoogleApiClient.Builder(this)
            .enableAutoManage(this, onConnectionFailedListener)
            .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
            .build()
}

/**
 * Gets the account that's currently signed in
 *
 * @return the active account (if existent) or null
 */
fun getActiveAccount(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

/**
 * Signs an existing Facebook account into the system
 *
 * @param callbackManager the Facebook async
 *                        authentication response manager
 * @param callback the Facebook async authentication response
 *                 handler
 */
fun AppCompatActivity.signInWithFacebook(callbackManager: CallbackManager,
                                         callback: FacebookCallback<LoginResult>) {
    LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
    LoginManager.getInstance().registerCallback(callbackManager, callback)
}

/**
 * Signs an existing Google account into the system
 *
 * @param googleApiClient the Google authentication API client
 * @param requestCode the authentication request code
 */
fun AppCompatActivity.signInWithGoogle(googleApiClient: GoogleApiClient, requestCode: Int) {
    val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
    startActivityForResult(intent, requestCode)
}
