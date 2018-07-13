package com.braincorp.petrolwatcher.feature.auth.authenticator

import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
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

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param requestCode the request code
     * @param onConnectionFailedListener the listener for connection
     *                                   failure events
     * @param resultCallback the result callback
     */
    fun signInWithGoogle(activity: AppCompatActivity, requestCode: Int,
                         onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener,
                         resultCallback: ResultCallback<GoogleSignInResult>)

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     * @param callback the Facebook async authentication response
     *                 handler
     * @param callbackManager the Facebook async
     *                        authentication response manager
     */
    fun signInWithFacebook(activity: AppCompatActivity,
                           callback: FacebookCallback<LoginResult>,
                           callbackManager: CallbackManager)

}