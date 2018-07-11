package com.braincorp.petrolwatcher.feature.auth.authenticator

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult

interface Authenticator {

    /**
     * Signs in with e-mail and password
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    fun signIn(email: String, password: String,
               onSuccessListener: OnSuccessListener<AuthResult>,
               onFailureListener: OnFailureListener)

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    fun signUp(email: String, password: String,
               onSuccessListener: OnSuccessListener<AuthResult>,
               onFailureListener: OnFailureListener)

}