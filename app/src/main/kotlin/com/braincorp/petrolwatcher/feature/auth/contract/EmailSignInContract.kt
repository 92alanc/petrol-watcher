package com.braincorp.petrolwatcher.feature.auth.contract

import com.braincorp.petrolwatcher.base.BaseContract

interface EmailSignInContract {
    interface View : BaseContract.View<Presenter> {
        // TODO: implement
    }

    interface Presenter : BaseContract.Presenter {
        fun signIn(email: String, password: String)
    }
}