package com.braincorp.petrolwatcher.feature.auth.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.viewmodel.Account
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient

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

fun Context.getActiveAccount(): Account? {
    return getActiveGoogleAccount()
}

private fun Context.getActiveGoogleAccount(): Account? {
    val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
    return if (googleAccount == null) {
        null
    } else {
        Account(googleAccount.displayName!!,
                googleAccount.email!!,
                googleAccount.photoUrl?.toString())
    }
}