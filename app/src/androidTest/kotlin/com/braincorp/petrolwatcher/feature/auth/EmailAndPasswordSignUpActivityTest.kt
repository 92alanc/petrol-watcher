package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.emailAndPasswordSignUp
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordSignUpActivityTest : BaseActivityTest<EmailAndPasswordSignUpActivity>(
        EmailAndPasswordSignUpActivity::class.java) {

    @Test
    @Ignore // This test will be ignored while there isn't a proper mock for Firebase implemented
    fun withValidCredentials_shouldRedirectToProfileActivity() {
        emailAndPasswordSignUp {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            redirectToProfileActivity()
        }
    }

    @Test
    fun withEmptyEmail_shouldShowError() {
        emailAndPasswordSignUp {
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            showEmptyEmailError()
        }
    }

    @Test
    fun withEmptyPassword_shouldShowError() {
        emailAndPasswordSignUp {
            typeEmail("test123@test.com")
            typeConfirmation("abcd1234")
        } clickNext {
            showEmptyPasswordError()
        }
    }

    @Test
    fun withEmptyConfirmation_shouldShowError() {
        emailAndPasswordSignUp {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
        } clickNext {
            showEmptyConfirmationError()
        }
    }

    @Test
    fun withPasswordAndConfirmationMismatch_shouldShowError() {
        emailAndPasswordSignUp {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd123")
        } clickNext {
            showPasswordAndConfirmationMismatchError()
        }
    }

}