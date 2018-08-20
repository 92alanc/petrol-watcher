package com.braincorp.petrolwatcher.feature.vehicles

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.robots.createVehicle
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateVehicleActivityTest : BaseActivityTest<CreateVehicleActivity>(
        CreateVehicleActivity::class.java) {

    @Test
    fun shouldStartActivityInAutoInputMode() {
        createVehicle {
            autoInputViewsAreVisible()
        }
    }

    @Test
    fun whenClickingOnManualInputMenuItem_shouldDisplayManualInputViews() {
        createVehicle {
        } clickManualInputMenuItem {
            manualInputViewsAreVisible()
        }
    }

    @Test
    fun whenClickingOnAutoInputMenuItem_shouldDisplayAutoInputViews() {
        createVehicle {
            clickManualInputMenuItem()
        } clickAutoInputMenuItem {
            autoInputViewsAreVisible()
        }
    }

}