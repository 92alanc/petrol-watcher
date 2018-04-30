package com.braincorp.petrolwatcher.feature.users.activities

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest : BaseActivityTest<LoginActivity>(LoginActivity::class.java) {

    @Before
    override fun before() {
        super.before()
        // TODO: sign out
    }

    @Test
    fun whenClickingOnSignUp_ShouldRedirectToSignUpScreen() {
        login {
        } clickOnSignUp {
            redirectsToSignUpScreen()
        }
    }

    @Test
    fun withCorrectEmailAndPassword_ShouldHaveSuccessfulLogin_WhenClickingOnSignIn() {
        login {
            typeEmail("alcam.ukdev@gmail.com")
            typePassword("abcd1234")
        } clickOnSignIn {
            loginIsSuccessful()
        }
    }

}