package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.feature.auth.MockAuthenticator
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator

class TestDependencyInjection : DependencyInjection() {

    override fun getAuthenticator(): Authenticator = MockAuthenticator()

}