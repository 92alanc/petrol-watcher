package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.view.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseActivityTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun whenClickingOnSignInWithEmail_shouldStartEmailSignInActivity() {
        mainActivity {
        } clickOnSignInWithEmail {
            redirectToEmailSignInActivity()
        }
    }

}