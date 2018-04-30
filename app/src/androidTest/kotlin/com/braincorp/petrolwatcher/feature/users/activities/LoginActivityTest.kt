package com.braincorp.petrolwatcher.feature.users.activities

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.users.activities.robots.login
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest : BaseActivityTest<LoginActivity>(LoginActivity::class.java) {

    @Before
    override fun before() {
        AuthenticationManager.signOut {
            super.before()
        }
    }

    @Test
    fun whenClickingOnSignUp_ShouldRedirectToSignUpScreen() {
        login {
        } clickOnSignUp {
            checkIfRedirectsToSignUpScreen()
        }
    }

    @Test
    fun withCorrectEmailAndPassword_ShouldHaveSuccessfulLogin() {
        login {
            typeEmail("alcam.ukdev@gmail.com")
            typePassword("abcd1234")
        } clickOnSignIn {
            checkIfLoginIsSuccessful()
        }
    }

    @Test
    fun withInvalidEmail_ShouldShowErrorDialogue() {
        login {
            typeEmail("invalid")
            typePassword("abcd1234")
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

    @Test
    fun withInvalidPassword_ShouldShowErrorDialogue() {
        login {
            typeEmail("alcam.ukdev@gmail.com")
            typePassword("invalid")
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

    @Test
    fun withInvalidEmailAndPassword_ShouldShowErrorDialogue() {
        login {
            typeEmail("invalid")
            typePassword("invalid")
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

    @Test
    fun withEmptyEmail_ShouldShowErrorDialogue() {
        login {
            typePassword("abcd1234")
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

    @Test
    fun withEmptyPassword_ShouldShowErrorDialogue() {
        login {
            typeEmail("alcam.ukdev@gmail.com")
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

    @Test
    fun withEmptyEmailAndPassword_ShouldShowErrorDialogue() {
        login {
        } clickOnSignIn {
            checkIfShowsErrorDialogue()
        }
    }

}