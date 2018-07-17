package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.robots.camera
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CameraActivityTest : BaseActivityTest<CameraActivity>(CameraActivity::class.java) {

    @Test
    fun shouldStartCamera() {
        camera {
            // TODO: implement
        }
    }

}