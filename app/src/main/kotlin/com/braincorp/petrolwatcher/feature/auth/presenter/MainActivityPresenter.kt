package com.braincorp.petrolwatcher.feature.auth.presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.contract.MainContract
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

/**
 * The implementation of the presentation layer
 * of the app's main activity
 *
 * @param view the view layer
 * @param authenticator the authenticator
 */
class MainActivityPresenter(private val view: MainContract.View,
                            private val authenticator: Authenticator) : MainContract.Presenter,
        GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    val callbackManager = authenticator.facebookCallbackManager

    /**
     * Function called when a connection with
     * the Google authentication server fails
     */
    override fun onConnectionFailed(result: ConnectionResult) {
        view.showErrorScreen(AuthErrorType.CONNECTION)
    }

    /**
     * Function called when an authentication with
     * the Facebook authentication server is successful
     */
    override fun onSuccess(result: LoginResult?) {
        view.showMap()
    }

    /**
     * Function called when a Facebook authentication
     * process is cancelled
     */
    override fun onCancel() {
        Log.i(TAG, "Facebook login cancelled")
    }

    /**
     * Function called when an error occurs during
     * a Facebook authentication attempt
     */
    override fun onError(error: FacebookException?) {
        view.showErrorScreen(AuthErrorType.FACEBOOK)
    }

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param requestCode the request code
     */
    override fun signInWithGoogle(activity: AppCompatActivity, requestCode: Int) {
        val intent = authenticator.signInWithGoogle(activity, this)
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     */
    override fun signInWithFacebook(activity: AppCompatActivity) {
        authenticator.signInWithFacebook(activity, this)
    }

    /**
     * Handles a Google sign in intent
     *
     * @param intent the intent
     */
    override fun handleGoogleSignInIntent(intent: Intent?) {
        val isSuccessful = authenticator.isGoogleSignInSuccessful(intent)

        if (isSuccessful)
            view.showMap()
        else
            view.showErrorScreen(AuthErrorType.GOOGLE)
    }

    /**
     * Determines whether the system has a
     * user already logged in
     */
    override fun isLoggedIn(): Boolean {
        return authenticator.getCurrentUser() != null
    }

}