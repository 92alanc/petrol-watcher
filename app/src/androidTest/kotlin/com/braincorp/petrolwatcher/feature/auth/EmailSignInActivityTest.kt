package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.emailSignIn
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailSignInActivityTest : BaseActivityTest<EmailSignInActivity>(EmailSignInActivity::class.java) {

    @Test
    fun whenClickingOnSignUp_shouldStartProfileActivity() {
        emailSignIn {
        } clickSignUp {
            redirectToProfileActivity()
        }
    }

}