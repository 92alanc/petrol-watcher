package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.profile
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest : BaseActivityTest<ProfileActivity>(ProfileActivity::class.java) {

    @Test
    fun should() {
        profile {
        } clickCamera {

        }
    }

}