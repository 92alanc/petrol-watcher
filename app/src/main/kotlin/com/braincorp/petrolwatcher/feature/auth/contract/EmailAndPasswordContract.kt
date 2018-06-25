package com.braincorp.petrolwatcher.feature.auth.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface EmailAndPasswordContract {
    interface View : BaseContract.View<Presenter> {
        fun showEmptyConfirmationError()
        fun showEmptyEmailError()
        fun showEmptyPasswordError()
        fun showPasswordNotMatchingError()
        fun showProfile()
    }

    interface Presenter : BaseContract.Presenter {
        fun validateCredentials(email: String, password: String, confirmation: String)
    }
}