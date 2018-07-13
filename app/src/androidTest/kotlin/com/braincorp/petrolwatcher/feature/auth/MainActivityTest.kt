package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.mainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseActivityTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun whenClickingOnSignInWithEmail_shouldStartEmailSignInActivity() {
        mainActivity {
        } clickSignInWithEmail {
            redirectToEmailSignInActivity()
        }
    }

    @Test
    fun whenClickingOnSignInWithGoogle_shouldRedirectToMapActivity() {
        mainActivity {
        } clickSignInWithGoogle {
            redirectToMapActivity()
        }
    }

}