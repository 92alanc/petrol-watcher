package com.braincorp.petrolwatcher.feature.auth.presenter

import android.content.Intent
import android.util.Log
import com.braincorp.petrolwatcher.feature.auth.contract.MainContract
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

/**
 * The implementation of the presentation layer
 * of the app's main activity
 *
 * @param view the view layer
 */
class MainActivityPresenter(private val view: MainContract.View) : MainContract.Presenter,
        GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    /**
     * Handles a Google sign in result, which result
     * can be understood as either a success or a failure
     *
     * @param data the intent received, containing
     *             the sign in result
     */
    override fun handleGoogleSignInResult(data: Intent?) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        Log.d(TAG, "result -> ${result.isSuccess}")
        if (result.isSuccess) {
            Log.d(TAG, "Google sign in successful")
            // TODO: start map activity
        }
    }

    /**
     * Function called when a connection with
     * the Google authentication server fails
     */
    override fun onConnectionFailed(result: ConnectionResult) {
        Log.w(TAG, "Connection failed! Result -> $result")
        // TODO
    }

    /**
     * Function called when an authentication with
     * the Facebook authentication server is successful
     */
    override fun onSuccess(result: LoginResult?) {
        Log.d(TAG, "Facebook login successful")
        // TODO: start map activity
    }

    /**
     * Function called when a Facebook authentication
     * process is cancelled
     */
    override fun onCancel() {
        Log.w(TAG, "Facebook login cancelled")
        // TODO
    }

    /**
     * Function called when an error occurs during
     * a Facebook authentication attempt
     */
    override fun onError(error: FacebookException?) {
        Log.e(TAG, error?.message, error)
        // TODO
    }

}