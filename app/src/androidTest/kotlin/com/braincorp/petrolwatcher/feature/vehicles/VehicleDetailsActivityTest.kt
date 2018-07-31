package com.braincorp.petrolwatcher.feature.vehicles

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailsActivityTest : BaseActivityTest<VehicleDetailsActivity>(
        VehicleDetailsActivity::class.java) {

    @Test
    fun whenClickingOnAutoInputMenuItem_shouldDisplayAutoInputViews() {
        vehicleDetails {
        } clickAutoInputMenuItem {
            autoInputViewsAreVisible()
        }
    }

    @Test
    fun whenClickingOnAutoInputMenuItem_shouldHideManualInputViews() {
        vehicleDetails {
        } clickAutoInputMenuItem {
            manualInputViewsAreNotVisible()
        }
    }

    @Test
    fun whenClickingOnManualInputMenuItem_shouldDisplayManualInputViews() {
        vehicleDetails {
        } clickManualInputMenuItem {
            manualInputViewsAreVisible()
        }
    }

    @Test
    fun whenClickingOnManualInputMenuItem_shouldHideAutoInputViews() {
        vehicleDetails {
        } clickManualInputMenuItem {
            autoInputViewsAreNotVisible()
        }
    }

}