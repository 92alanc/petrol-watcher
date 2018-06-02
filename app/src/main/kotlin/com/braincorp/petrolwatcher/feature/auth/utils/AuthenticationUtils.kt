package com.braincorp.petrolwatcher.feature.auth.utils

import android.content.Context
import com.braincorp.petrolwatcher.feature.auth.viewmodel.Account
import com.google.android.gms.auth.api.signin.GoogleSignIn

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