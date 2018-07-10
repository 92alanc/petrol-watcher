package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.authenticator.FirebaseAuthenticator

open class DependencyInjection {

    open fun getAuthenticator(): Authenticator {
        return FirebaseAuthenticator()
    }

}