package com.braincorp.petrolwatcher.feature.vehicles

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
            selectedYearIs(2011)
        }
    }

    @Test
    fun shouldDisplaySelectedManufacturer() {
        mockYearsResponse()
        mockManufacturersResponse()

        createVehicle {
            selectYear(position = 1)
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
            selectYear(position = 1)
            selectManufacturer(position = 3)
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
            selectYear(position = 1)
            selectManufacturer(position = 3)
            selectModel(position = 2)
            detailsPositionIs(3)
        } selectDetails {
            selectedDetailsIs("2.0 TDI Coupe Quattro")
        }
    }

    @Test
    fun whenRotatingDevice_inAutoInput_shouldKeepYear() {
        mockYearsResponse()

        createVehicle {
            selectYear(position = 2)
        } rotateDevice {
            selectedYearIs(2012)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inManualInput_shouldKeepYear() {
        createVehicle {
            clickManualInputMenuItem()
            typeYear(2018)
        } rotateDevice {
            yearIs(2018)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenChangingToManualInput_shouldKeepYear() {
        mockYearsResponse()

        createVehicle {
            selectYear(position = 1)
        } clickManualInputMenuItem {
            yearIs(2011)
        }
    }

    @Test
    fun whenRotatingDevice_inAutoInput_shouldKeepManufacturer() {
        mockYearsResponse()
        mockManufacturersResponse()

        createVehicle {
            selectYear(position = 2)
            selectManufacturer(position = 1)
        } rotateDevice {
            selectedManufacturerIs("Alfa Romeo")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inManualInput_shouldKeepManufacturer() {
        createVehicle {
            clickManualInputMenuItem()
            typeManufacturer("Land Rover")
        } rotateDevice {
            manufacturerIs("Land Rover")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenChangingToManualInput_shouldKeepManufacturer() {
        mockYearsResponse()
        mockManufacturersResponse()

        createVehicle {
            selectYear(position = 1)
            selectManufacturer(position = 1)
        } clickManualInputMenuItem {
            manufacturerIs("Alfa Romeo")
        }
    }

    @Test
    fun whenRotatingDevice_inAutoInput_shouldKeepModel() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()

        createVehicle {
            selectYear(position = 2)
            selectManufacturer(position = 1)
            selectModel(position = 3)
        } rotateDevice {
            selectedModelIs("A5")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inManualInput_shouldKeepModel() {
        createVehicle {
            clickManualInputMenuItem()
            typeModel("Golf")
        } rotateDevice {
            modelIs("Golf")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenChangingToManualInput_shouldKeepModel() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()

        createVehicle {
            selectYear(position = 1)
            selectManufacturer(position = 1)
            selectModel(position = 3)
        } clickManualInputMenuItem {
            modelIs("A5")
        }
    }

    @Test
    fun whenRotatingDevice_inAutoInput_shouldKeepDetails() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()
        mockDetailsResponse()

        createVehicle {
            selectYear(position = 2)
            selectManufacturer(position = 1)
            selectModel(position = 3)
            selectDetails(position = 2)
        } rotateDevice {
            selectedDetailsIs("2.0 TDI Convertible Quattro")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inManualInput_shouldKeepDetails() {
        createVehicle {
            clickManualInputMenuItem()
            typeTrimLevel("1.8")
            typeFuelCapacity(60)
            typeAvgConsumptionCity(8.4f)
            typeAvgConsumptionMotorway(10.2f)
        } rotateDevice {
            trimLevelIs("1.8")
            fuelCapacityIs(60)
            avgConsumptionCityIs(8.4f)
            avgConsumptionMotorwayIs(10.2f)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenChangingToManualInput_shouldKeepDetails() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()
        mockDetailsResponse()

        createVehicle {
            selectYear(position = 1)
            selectManufacturer(position = 1)
            selectModel(position = 1)
            selectDetails(position = 2)
        } clickManualInputMenuItem {
            trimLevelIs("2.0 TDI Convertible Quattro")
            fuelCapacityIs(60)
            avgConsumptionCityIs(8.4f)
            avgConsumptionMotorwayIs(10.2f)
        }
    }

    @Test
    fun whenClickingOnSave_withValidData_shouldRedirectToVehicleListActivity() {
        mockYearsResponse()
        mockManufacturersResponse()
        mockModelsResponse()
        mockDetailsResponse()

        createVehicle {
            selectYear(position = 1)
            selectManufacturer(position = 1)
            selectModel(position = 1)
            selectDetails(position = 2)
        } clickSave {
            redirectToVehicleListActivity()
        }
    }

    @Test
    fun whenClickingOnSave_withInvalidData_shouldShowDialogue() {
        createVehicle {
        } clickSave {
            showDialogue()
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

    private fun setDeviceRotationToPortrait() {
        rule.activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
    }

}