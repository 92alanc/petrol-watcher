package com.braincorp.petrolwatcher.feature.auth.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface EmailAndPasswordSignUpContract {
    interface View : BaseContract.View<Presenter> {
        fun showEmptyConfirmationError()
        fun showEmptyEmailError()
        fun showEmptyPasswordError()
        fun showPasswordNotMatchingError()
        fun showProfile()
    }

    interface Presenter : BaseContract.Presenter {
        fun createAccount(email: String, password: String, confirmation: String)
    }
}