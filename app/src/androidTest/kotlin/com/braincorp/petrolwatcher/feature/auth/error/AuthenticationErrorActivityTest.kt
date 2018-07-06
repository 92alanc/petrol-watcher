package com.braincorp.petrolwatcher.feature.auth.error

import android.content.Intent
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationErrorActivityTest : BaseActivityTest<AuthenticationErrorActivity>(
        AuthenticationErrorActivity::class.java) {

    // TODO: implement tests

    override fun intent(): Intent {
        val intent = super.intent()
        intent.putExtra("error_type", AuthErrorType.CONNECTION)
        return intent
    }

    @Test
    fun whenClickingOnTryAgain_shouldCloseScreen() {
        authenticationError {
        } clickTryAgain {
            screenIsClosed()
        }
    }

    @Test
    fun withConnectionError_shouldDisplayCorrectImage() {

    }

    @Test
    fun withConnectionError_shouldDisplayCorrectMessage() {

    }

    @Test
    fun withEmailAndPasswordError_shouldDisplayCorrectImage() {

    }

    @Test
    fun withEmailAndPasswordError_shouldDisplayCorrectMessage() {

    }

    @Test
    fun withFacebookError_shouldDisplayCorrectImage() {

    }

    @Test
    fun withFacebookError_shouldDisplayCorrectMessage() {

    }

    @Test
    fun withGoogleError_shouldDisplayCorrectImage() {

    }

    @Test
    fun withGoogleError_shouldDisplayCorrectMessage() {

    }

}