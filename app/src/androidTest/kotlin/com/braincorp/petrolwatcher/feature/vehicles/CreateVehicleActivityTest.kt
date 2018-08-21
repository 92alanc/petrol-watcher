package com.braincorp.petrolwatcher.feature.vehicles

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.robots.createVehicle
import okhttp3.mockwebserver.MockResponse
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

    @Test
    fun shouldDisplaySelectedYear() {
        mockYearsResponse()

        createVehicle {
            yearPositionIs(1)
        } selectYear {
            selectedYearIs(1942)
        }
    }

    @Test
    fun shouldDisplaySelectedManufacturer() {
        mockYearsResponse()
        mockManufacturersResponse()

        createVehicle {
            yearPositionIs(1)
            selectYear()
            manufacturerPositionIs(3)
        } selectManufacturer {
            selectedManufacturerIs("Audi")
        }
    }

    @Test
    fun shouldDisplaySelectedModel() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()

        createVehicle {
            yearPositionIs(1)
            selectYear()
            manufacturerPositionIs(3)
            selectManufacturer()
            modelPositionIs(2)
        } selectModel {
            selectedModelIs("A4")
        }
    }

    @Test
    fun shouldDisplaySelectedDetails() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()
        mockDetailsResponse()

        createVehicle {
            yearPositionIs(1)
            selectYear()
            manufacturerPositionIs(3)
            selectManufacturer()
            modelPositionIs(2)
            selectModel()
            detailsPositionIs(3)
        } selectDetails {
            selectedDetailsIs("2.0 TDI Coupe Quattro")
        }
    }

    private fun mockYearsResponse() {
        val yearsBody = getJsonFromAsset("response_years.txt")
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(yearsBody))
    }

    private fun mockManufacturersResponse() {
        val manufacturersBody = getJsonFromAsset("response_manufacturers.txt")
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(manufacturersBody))
    }

    private fun mockModelsResponse() {
        val modelsBody = getJsonFromAsset("response_models.txt")
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(modelsBody))
    }

    private fun mockDetailsResponse() {
        val detailsBody = getJsonFromAsset("response_details.txt")
        mockVehicleApi.enqueue(MockResponse().setResponseCode(200).setBody(detailsBody))
    }

}