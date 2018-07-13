package com.braincorp.petrolwatcher.feature.auth.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.contract.MainContract
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback

/**
 * The implementation of the presentation layer
 * of the app's main activity
 *
 * @param view the view layer
 */
class MainActivityPresenter(private val view: MainContract.View,
                            private val authenticator: Authenticator) : MainContract.Presenter,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<GoogleSignInResult>,
        FacebookCallback<LoginResult> {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    val callbackManager: CallbackManager = CallbackManager.Factory.create()

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
        authenticator.signInWithGoogle(activity, requestCode, this,
                this)
    }

    override fun onResult(result: GoogleSignInResult) {
        if (result.isSuccess)
            view.showMap()
        else
            view.showErrorScreen(AuthErrorType.GOOGLE)
    }

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     */
    override fun signInWithFacebook(activity: AppCompatActivity) {
        authenticator.signInWithFacebook(activity, this, callbackManager)
    }

}