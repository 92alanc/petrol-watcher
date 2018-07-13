package com.braincorp.petrolwatcher.feature.auth.authenticator

import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * A Firebase authentication wrapper
 */
class FirebaseAuthenticator : Authenticator {

    /**
     * Signs in with e-mail and password
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signIn(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
    }

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signUp(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
    }

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param requestCode the request code
     * @param onConnectionFailedListener the listener for connection
     *                                   failure events
     */
    override fun signInWithGoogle(activity: AppCompatActivity, requestCode: Int,
                                  onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener,
                                  resultCallback: ResultCallback<GoogleSignInResult>) {
        val signInOptions = GoogleSignInOptions.Builder(DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val client = GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, onConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build()

        val pendingResult = Auth.GoogleSignInApi.silentSignIn(client)
        pendingResult.setResultCallback(resultCallback)

        /*val intent = Auth.GoogleSignInApi.getSignInIntent(client)
        activity.startActivityForResult(intent, requestCode)*/
    }

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     * @param callback the Facebook async authentication response
     *                 handler
     * @param callbackManager the Facebook async
     *                        authentication response manager
     */
    override fun signInWithFacebook(activity: AppCompatActivity,
                                    callback: FacebookCallback<LoginResult>,
                                    callbackManager: CallbackManager) {
        LoginManager.getInstance().logInWithReadPermissions(activity, listOf("email"))
        LoginManager.getInstance().registerCallback(callbackManager, callback)
    }
}