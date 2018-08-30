package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.robots.vehicleDetails
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailsActivityTest : BaseActivityTest<VehicleDetailsActivity>(
        VehicleDetailsActivity::class.java) {

    override fun intent(): Intent {
        val intent = super.intent()
        val vehicle = Vehicle(manufacturer = "Ford",
                model = "Focus",
                year = 2015,
                details = Vehicle.Details(trimLevel = "1.4",
                        fuelCapacity = 50,
                        avgConsumptionCity = 10.2f,
                        avgConsumptionMotorway = 14.5f),
                calculatedValues = Vehicle.CalculatedValues(avgConsumptionCity = 9.8f,
                        avgConsumptionMotorway = 13.2f))
        intent.putExtra("vehicle", vehicle)
        return intent
    }

    @Test
    fun shouldStartDisplayingReadOnlyFields() {
        vehicleDetails {
            readOnlyFieldsAreDisplayed()
        }
    }

    @Test
    fun shouldStartDisplayingCalculatedData() {
        vehicleDetails {
            calculatedDataAreDisplayed()
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldDisplayEditableFields() {
        vehicleDetails {
        } clickFab {
            editableFieldsAreDisplayed()
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowYear() {
        vehicleDetails {
        } clickFab {
            yearInEditModeIs(2015)
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowManufacturer() {
        vehicleDetails {
        } clickFab {
            manufacturerIs("Ford")
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowModel() {
        vehicleDetails {
        } clickFab {
            modelIs("Focus")
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowTrimLevel() {
        vehicleDetails {
        } clickFab {
            trimLevelIs("1.4")
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowFuelCapacity() {
        vehicleDetails {
        } clickFab {
            fuelCapacityInEditModeIs(50)
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowAvgConsumptionCity() {
        vehicleDetails {
        } clickFab {
            avgConsumptionCityInEditModeIs(10.2f)
        }
    }

    @Test
    fun whenClickingOnEditButton_shouldShowAvgConsumptionMotorway() {
        vehicleDetails {
        } clickFab {
            avgConsumptionMotorwayInEditModeIs(14.5f)
        }
    }

    @Test
    fun whenClickingOnSaveButton_withInvalidData_shouldShowErrorDialogue() {
        vehicleDetails {
            clickFab()
            typeYear(0)
        } clickFab {
            showDialogue()
        }
    }

    @Test
    fun whenClickingOnSaveButton_withValidData_shouldRedirectToVehicleListActivity() {
        vehicleDetails {
            clickFab()
            typeYear(2016)
            typeManufacturer("Vauxhall")
            typeModel("Meriva")
            typeTrimLevel("1.6")
            typeFuelCapacity(60)
            typeAvgConsumptionCity(8.2f)
            typeAvgConsumptionMotorway(10f)
        } clickFab {
            redirectToVehicleListActivity()
        }
    }

    @Test
    fun whenRotatingDevice_inViewMode_shouldKeepName() {
        vehicleDetails {
        } rotateDevice {
            nameIs("Ford Focus 1.4")
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inViewMode_shouldKeepYear() {
        vehicleDetails {
        } rotateDevice {
            yearInViewModeIs(2015)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inViewMode_shouldKeepFuelCapacity() {
        vehicleDetails {
        } rotateDevice {
            fuelCapacityInViewModeIs(50)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inViewMode_shouldKeepAvgConsumptionCity() {
        vehicleDetails {
        } rotateDevice {
            avgConsumptionCityInViewModeIs(10.2f)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_inViewMode_shouldKeepAvgConsumptionMotorway() {
        vehicleDetails {
        } rotateDevice {
            avgConsumptionMotorwayInViewModeIs(14.5f)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_shouldKeepCalculatedAvgConsumptionCity() {
        vehicleDetails {
        } rotateDevice {
            calculatedAvgConsumptionCityIs(9.8f)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenRotatingDevice_shouldKeepCalculatedAvgConsumptionMotorway() {
        vehicleDetails {
        } rotateDevice {
            calculatedAvgConsumptionMotorwayIs(13.2f)
        }

        setDeviceRotationToPortrait()
    }

    @Test
    fun whenClickingOnCalculate_shouldRedirectToConsumptionActivity() {
        vehicleDetails {
        } clickCalculate {
            redirectToConsumptionActivity()
        }
    }

    private fun setDeviceRotationToPortrait() {
        rule.activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
    }

}