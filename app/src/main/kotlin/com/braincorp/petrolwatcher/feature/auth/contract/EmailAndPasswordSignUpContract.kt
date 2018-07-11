package com.braincorp.petrolwatcher.feature.auth.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface EmailAndPasswordSignUpContract {
    /**
     * The view layer of the e-mail and password
     * sign up method
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows an error when the password confirmation
         * field is empty
         */
        fun showEmptyConfirmationError()

        /**
         * Shows an error when the e-mail field is empty
         */
        fun showEmptyEmailError()

        /**
         * Shows an error when the password field is empty
         */
        fun showEmptyPasswordError()

        /**
         * Shows an error when the password and confirmation
         * don't match
         */
        fun showPasswordNotMatchingError()

        /**
         * Shows the profile
         */
        fun showProfile()

        /**
         * Shows a backend error dialogue
         */
        fun showBackendError()

        /**
         * Shows an e-mail format error
         */
        fun showEmailFormatError()
    }

    /**
     * The presentation layer of the e-mail and password
     * sign up method
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Creates an e-mail and password based account
         *
         * @param email the e-mail address
         * @param password the password
         * @param confirmation the password confirmation (must
         *                     be equal to password)
         */
        fun createAccount(email: String, password: String, confirmation: String)
    }
}