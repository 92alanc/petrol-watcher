package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import android.support.v7.app.AlertDialog
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailAndPasswordSignUpPresenter
import com.braincorp.petrolwatcher.feature.auth.robots.emailAndPasswordSignUp
import com.braincorp.petrolwatcher.utils.startProfileActivity
import com.google.firebase.auth.AuthResult
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordSignUpActivityTest : BaseActivityTest<EmailAndPasswordSignUpActivity>(
        EmailAndPasswordSignUpActivity::class.java) {

    @Mock
    private val mockPresenter = mock(EmailAndPasswordSignUpPresenter::class.java)

    @Mock
    private val result = mock(AuthResult::class.java)

    @Test
    fun withValidCredentials_shouldRedirectToProfileActivity() {
        setupMocks()

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
        setupMocks()

        emailAndPasswordSignUp {
            typeEmail("error@test.com")
            typePassword("abcd1234")
            typeConfirmation("abcd1234")
        } clickNext {
            showErrorDialogue()
        }
    }

    private fun setupMocks() {
        val exception = Exception()

        `when`(mockPresenter.createAccount("test123@test.com", "abcd1234",
                "abcd1234")).then {
            mockPresenter.onSuccess(result)
        }

        `when`(mockPresenter.createAccount("error@test.com", "abcd1234",
                "abcd1234")).then {
            mockPresenter.onFailure(exception)
        }

        `when`(mockPresenter.onSuccess(result)).then {
            // TODO: use thenCallRealMethod()
            rule.activity.startProfileActivity()
        }

        `when`(mockPresenter.onFailure(exception)).then {
            // TODO: use thenCallRealMethod()
            AlertDialog.Builder(rule.activity)
                    .setTitle(R.string.error)
                    .setIcon(R.drawable.ic_error)
                    .setMessage(R.string.error_creating_account)
                    .setNeutralButton(R.string.ok, null)
                    .show()
        }

        rule.activity.presenter = mockPresenter
    }

}