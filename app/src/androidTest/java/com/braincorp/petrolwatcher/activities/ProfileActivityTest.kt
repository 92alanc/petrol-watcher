package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.activities.robots.ProfileActivityRobot
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {

    private val robot = ProfileActivityRobot()

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun shouldDisplayHeaderWhenOnCreateMode() {
        robot.launchActivity(UiMode.CREATE)
                .checkIfHeaderIsVisible()
    }

    @Test
    fun headerShouldDisplayEmailAndPasswordTextWhenOnFirstStepOfCreateMode() {
        robot.launchActivity(UiMode.CREATE)
                .checkIfHeaderShowsEmailAndPasswordText()
    }

    @Test
    fun headerShouldNotBeVisibleWhenOnEditMode() {
        robot.launchActivity(UiMode.EDIT)
                .checkIfHeaderIsNotVisible()
    }

    @Test
    fun headerShouldNotBeVisibleWhenOnViewMode() {
        robot.launchActivity(UiMode.VIEW)
                .checkIfHeaderIsNotVisible()
    }

    @Test
    fun shouldShowChangesNotSavedDialogueWhenBackButtonIsPressedInEditMode() {
        robot.launchActivity(UiMode.EDIT)
                .pressBackButton()
        robot.checkIfShowsChangesNotSavedDialogue()
    }

    @Test
    fun vehiclesButtonShouldBeVisibleWhenOnViewMode() {
        robot.launchActivity(UiMode.VIEW)
                .checkIfVehiclesButtonIsVisible()
    }

    @Test
    fun vehiclesButtonShouldNotBeVisibleWhenOnCreateMode() {
        robot.launchActivity(UiMode.CREATE)
                .checkIfVehiclesButtonIsNotVisible()
    }

    @Test
    fun vehiclesButtonShouldNotBeVisibleWhenOnEditMode() {
        robot.launchActivity(UiMode.EDIT)
                .checkIfVehiclesButtonIsNotVisible()
    }

    @Test
    fun shouldLaunchVehiclesActivityWhenClickingOnVehiclesButton() {
        robot.launchActivity(UiMode.VIEW)
                .clickOnVehiclesButton()
                .checkIfLaunchesVehiclesActivity()
    }

}