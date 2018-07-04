package com.braincorp.petrolwatcher.feature.auth.contract

import android.content.Intent
import com.braincorp.petrolwatcher.base.BaseContract

interface MainContract {
    /**
     * The view layer of the app's main activity
     */
    interface View : BaseContract.View<Presenter> {
        // TODO: implement
    }

    /**
     * The presentation layer of the app's main activity
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Handles a Google sign in result, which result
         * can be understood as either a success or a failure
         *
         * @param data the intent received, containing
         *             the sign in result
         */
        fun handleGoogleSignInResult(data: Intent?)
    }
}