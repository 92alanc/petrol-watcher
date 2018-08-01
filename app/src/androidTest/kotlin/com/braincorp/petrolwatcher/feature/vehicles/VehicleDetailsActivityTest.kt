package com.braincorp.petrolwatcher.feature.vehicles

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.api.RESPONSE_YEARS
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.junit.runner.RunWith

// FIXME
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

    @Test
    fun withYearRange_shouldFillSpinner() {
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(RESPONSE_YEARS))

        vehicleDetails {
        } clickYearsSpinner {

        }
    }

}