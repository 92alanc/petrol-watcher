package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.authenticator.MockAuthenticator
import com.braincorp.petrolwatcher.feature.auth.robots.mainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseActivityTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun whenClickingOnSignInWithEmail_shouldStartEmailSignInActivity() {
        mainActivity {
        } clickSignInWithEmail {
            redirectToEmailSignInActivity()
        }
    }

    @Test
    fun whenClickingOnSignInWithGoogle_shouldRedirectToMapActivity() {
        setAuthSuccess(true)
        mainActivity {
        } clickSignInWithGoogle {
            redirectToMapActivity()
        }
    }

    @Test
    fun withGoogleSignInError_shouldShowErrorScreen() {
        setAuthSuccess(false)
        mainActivity {
        } clickSignInWithGoogle {
            showErrorScreen()
        }
    }

    @Test
    fun whenClickingOnSignInWithFacebook_shouldRedirectToMapActivity() {
        setAuthSuccess(true)
        mainActivity {
        } clickSignInWithFacebook {
            redirectToMapActivity()
        }
    }

    @Test
    fun withFacebookError_shouldShowErrorScreen() {
        setAuthSuccess(false)
        mainActivity {
        } clickSignInWithFacebook {
            showErrorScreen()
        }
    }

    @Test
    fun withUserAlreadyLoggedIn_shouldRedirectToMapActivity() {
        setUserLoggedIn(true)
        mainActivity {
            redirectToMapActivity()
        }
    }

    private fun setAuthSuccess(success: Boolean) {
        (getAuthenticator() as MockAuthenticator).authSuccess = success
    }

    private fun setUserLoggedIn(userLoggedIn: Boolean) {
        (getAuthenticator() as MockAuthenticator).userLoggedIn = userLoggedIn
    }

}