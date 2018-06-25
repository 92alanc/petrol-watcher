package com.braincorp.petrolwatcher.feature.auth.contract

import android.content.Intent
import com.braincorp.petrolwatcher.base.BaseContract

interface MainContract {
    interface View : BaseContract.View<Presenter> {
        // TODO: implement
    }

    interface Presenter : BaseContract.Presenter {
        fun handleGoogleSignInResult(data: Intent?)
    }
}