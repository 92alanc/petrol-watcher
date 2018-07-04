package com.braincorp.petrolwatcher.feature.auth.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface EmailSignInContract {
    /**
     * The view layer of the e-mail and password
     * sign in method
     */
    interface View : BaseContract.View<Presenter> {
        // TODO: implement
    }

    /**
     * The presentation layer of the e-mail and password
     * sign in method
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Signs in an existing e-mail and password
         * based account
         *
         * @param email the e-mail address
         * @param password the password
         */
        fun signIn(email: String, password: String)
    }
}