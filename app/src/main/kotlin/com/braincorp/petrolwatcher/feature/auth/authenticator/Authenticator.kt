package com.braincorp.petrolwatcher.feature.auth.authenticator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult

interface Authenticator {

    val facebookCallbackManager: CallbackManager

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

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param onConnectionFailedListener the listener for connection
     *                                   failure events
     *
     * @return the Google sign in intent
     */
    fun signInWithGoogle(activity: AppCompatActivity,
                         onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener): Intent

    /**
     * Determines whether a Google sign in intent
     * is successful
     *
     * @param intent the Google sign in intent
     *
     * @return true if positive, otherwise false
     */
    fun isGoogleSignInSuccessful(intent: Intent?): Boolean

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     * @param callback the Facebook async authentication response
     *                 handler
     */
    fun signInWithFacebook(activity: AppCompatActivity,
                           callback: FacebookCallback<LoginResult>)

}