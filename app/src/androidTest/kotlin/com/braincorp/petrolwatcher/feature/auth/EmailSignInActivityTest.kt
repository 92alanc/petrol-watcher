package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.emailSignIn
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailSignInActivityTest : BaseActivityTest<EmailSignInActivity>(EmailSignInActivity::class.java) {

    @Test
    fun withValidCredentials_shouldRedirectToMapActivity() {
        emailSignIn {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
        } clickSignIn {
            redirectToMapActivity()
        }
    }

    @Test
    fun whenClickingOnSignUp_shouldStartEmailAndPasswordActivity() {
        emailSignIn {
        } clickSignUp {
            redirectToEmailAndPasswordSignUpActivity()
        }
    }

}