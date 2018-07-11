package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailAndPasswordSignUpPresenter
import com.braincorp.petrolwatcher.feature.auth.robots.emailAndPasswordSignUp
import com.braincorp.petrolwatcher.utils.startProfileActivity
import com.google.firebase.auth.AuthResult
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordSignUpActivityTest : BaseActivityTest<EmailAndPasswordSignUpActivity>(
        EmailAndPasswordSignUpActivity::class.java) {

    @Mock
    private val presenter = mock(EmailAndPasswordSignUpPresenter::class.java)

    @Mock
    private val result = mock(AuthResult::class.java)

    @Before
    override fun setup() {
        super.setup()
        setupMocks()
    }

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

    private fun setupMocks() {
        `when`(presenter.createAccount("test123@test.com", "abcd1234",
                "abcd1234")).then {
            presenter.onSuccess(result)
        }

        `when`(presenter.createAccount("error@test.com", "abcd1234",
                "abcd1234")).then {
            presenter.onFailure(Exception())
        }

        `when`(presenter.createAccount("", "abcd1234", "abcd1234"))
                .thenCallRealMethod()

        `when`(presenter.createAccount("test123@test.com", "",
                "abcd1234")).thenCallRealMethod()

        `when`(presenter.createAccount("test123@test.com", "abcd1234",
                "")).thenCallRealMethod()

        `when`(presenter.createAccount("test123@test.com", "abcd1234",
                "abcd123")).thenCallRealMethod()

        `when`(presenter.createAccount("test.com", "abcd1234",
                "abcd1234")).thenCallRealMethod()

        `when`(presenter.onSuccess(result)).then {
            rule.activity.startProfileActivity()
        }

        `when`(presenter.onFailure(Exception())).thenCallRealMethod()

        rule.activity.presenter = presenter
    }

}