package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Intent
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import com.braincorp.petrolwatcher.ui.MultiStateUi
import okhttp3.mockwebserver.MockResponse
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailsActivityCreationTest : BaseActivityTest<VehicleDetailsActivity>(
        VehicleDetailsActivity::class.java) {

    private val uiState = MultiStateUi.State.CREATION

    @Test
    @Ignore
    fun whenClickingOnAutoInputMenuItem_shouldDisplayAutoInputViews() {
        vehicleDetails {
        } clickAutoInputMenuItem {
            autoInputViewsAreVisible()
        }
    }

    @Test
    @Ignore
    fun whenClickingOnAutoInputMenuItem_shouldHideManualInputViews() {
        vehicleDetails {
        } clickAutoInputMenuItem {
            manualInputViewsAreNotVisible()
        }
    }

    @Test
    @Ignore
    fun whenClickingOnManualInputMenuItem_shouldDisplayManualInputViews() {
        vehicleDetails {
        } clickManualInputMenuItem {
            manualInputViewsAreVisible()
        }
    }

    @Test
    @Ignore
    fun whenClickingOnManualInputMenuItem_shouldHideAutoInputViews() {
        vehicleDetails {
        } clickManualInputMenuItem {
            autoInputViewsAreNotVisible()
        }
    }

    @Test
    @Ignore
    fun withYearRange_shouldFillSpinner() {
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(getJsonFromAsset("response_years")))

        vehicleDetails {
        } clickYearsSpinner {

        }
    }

    override fun intent(): Intent {
        val intent = super.intent()
        intent.putExtra("ui_state", uiState)
        return intent
    }

}