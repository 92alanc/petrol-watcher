package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import org.mockito.Mockito.mock

class TestDependencyInjection : DependencyInjection() {

    override fun getAuthenticator(): Authenticator = mock(Authenticator::class.java)

}