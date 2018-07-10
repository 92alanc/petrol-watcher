package com.braincorp.petrolwatcher.feature.auth.authenticator

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult

interface Authenticator {

    fun signIn(email: String, password: String,
               onSuccessListener: OnSuccessListener<AuthResult>,
               onFailureListener: OnFailureListener)

}