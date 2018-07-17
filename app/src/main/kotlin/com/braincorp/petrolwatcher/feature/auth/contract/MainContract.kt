package com.braincorp.petrolwatcher.feature.auth.contract

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.base.BaseContract
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType

interface MainContract {
    /**
     * The view layer of the app's main activity
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows an authentication error screen
         *
         * @param errorType the authentication error type
         */
        fun showErrorScreen(errorType: AuthErrorType)

        /**
         * Shows the map activity
         */
        fun showMap()
    }

    /**
     * The presentation layer of the app's main activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Signs in with a Google account
         *
         * @param activity the activity
         * @param requestCode the request code
         */
        fun signInWithGoogle(activity: AppCompatActivity, requestCode: Int)

        /**
         * Signs in with a Facebook account
         *
         * @param activity the activity
         */
        fun signInWithFacebook(activity: AppCompatActivity)

        /**
         * Handles a Google sign in intent
         *
         * @param intent the intent
         */
        fun handleGoogleSignInIntent(intent: Intent?)
    }
}