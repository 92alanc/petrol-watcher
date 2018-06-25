package com.braincorp.petrolwatcher.feature.auth.contract

interface EmailAndPasswordContract {
    interface View {
        fun showEmptyConfirmationError()
        fun showEmptyEmailError()
        fun showEmptyPasswordError()
        fun showPasswordNotMatchingError()
        fun showProfile()
    }

    interface Controller {
        fun validateCredentials(email: String, password: String, confirmation: String)
    }
}