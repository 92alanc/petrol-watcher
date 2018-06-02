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

fun getActiveAccount(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

fun AppCompatActivity.signInWithFacebook(callbackManager: CallbackManager,
                                         callback: FacebookCallback<LoginResult>) {
    LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
    LoginManager.getInstance().registerCallback(callbackManager, callback)
}

fun AppCompatActivity.signInWithGoogle(googleApiClient: GoogleApiClient, requestCode: Int) {
    val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
    startActivityForResult(intent, requestCode)
}
