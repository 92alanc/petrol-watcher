package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailSignInActivityPresenter
import com.braincorp.petrolwatcher.feature.auth.robots.emailSignIn
import com.braincorp.petrolwatcher.utils.startMapActivity
import com.google.firebase.auth.AuthResult
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class EmailSignInActivityTest : BaseActivityTest<EmailSignInActivity>(EmailSignInActivity::class.java) {

    @Mock
    private val presenter = mock(EmailSignInActivityPresenter::class.java)

    @Mock
    private val result = mock(AuthResult::class.java)

    @Before
    override fun setup() {
        super.setup()
        setupMocks()
    }

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

    private fun setupMocks() {
        `when`(presenter.signIn("test123@test.com", "abcd1234")).then {
            presenter.onSuccess(result)
        }

        `when`(presenter.onSuccess(result)).then {
            rule.activity.startMapActivity()
        }

        rule.activity.presenter = presenter
    }

}