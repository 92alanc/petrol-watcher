package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.emailAndPasswordSignUp
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordSignUpActivityTest : BaseActivityTest<EmailAndPasswordSignUpActivity>(
        EmailAndPasswordSignUpActivity::class.java) {

    @Test
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
            showEmailFormatError()
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

    @Test
    fun withInvalidEmailFormat_shouldShowError() {
        emailAndPasswordSignUp {
            typeEmail("test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            showEmailFormatError()
        }
    }

    @Test
    fun withAuthenticationError_shouldShowErrorDialogue() {
        emailAndPasswordSignUp {
            typeEmail("error@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            showErrorDialogue()
        }
    }

    @Test
    fun withInvalidPasswordLength_shouldShowErrorDialogue() {
        emailAndPasswordSignUp {
            typeEmail("test123@test.com")
            typePassword("short")
            typeConfirmation("short")
        } clickNext {
            showPasswordLengthWarningDialogue()
        }
    }

}