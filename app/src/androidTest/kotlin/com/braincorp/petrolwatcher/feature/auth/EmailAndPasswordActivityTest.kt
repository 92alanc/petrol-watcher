package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.emailAndPassword
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordActivityTest : BaseActivityTest<EmailAndPasswordActivity>(
        EmailAndPasswordActivity::class.java) {

    @Test
    fun withValidCredentials_shouldRedirectToProfileActivity() {
        emailAndPassword {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            redirectToProfileActivity()
        }
    }

    @Test
    fun withEmptyEmail_shouldShowError() {
        emailAndPassword {
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            showEmptyEmailError()
        }
    }

    @Test
    fun withEmptyPassword_shouldShowError() {
        emailAndPassword {
            typeEmail("test123@test.com")
            typeConfirmation("abcd1234")
        } clickNext {
            showEmptyPasswordError()
        }
    }

    @Test
    fun withEmptyConfirmation_shouldShowError() {
        emailAndPassword {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
        } clickNext {
            showEmptyConfirmationError()
        }
    }

    @Test
    fun withPasswordAndConfirmationMismatch_shouldShowError() {
        emailAndPassword {
            typeEmail("test123@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd123")
        } clickNext {
            showPasswordAndConfirmationMismatchError()
        }
    }

}