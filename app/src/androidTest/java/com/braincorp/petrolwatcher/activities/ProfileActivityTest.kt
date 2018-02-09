package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.activities.robots.ProfileActivityRobot
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {

    private val robot = ProfileActivityRobot()

    @Before
    fun setup() {
        if (FirebaseAuth.getInstance().currentUser?.email != "alcam.ukdev@gmail.com") {
            AuthenticationManager.signOut {
                AuthenticationManager.signIn("alcam.ukdev@gmail.com", "abcd1234",
                        onSuccessAction = { }, onFailureAction = { })
            }
            robot.wait(1000)
        }
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun shouldDisplayHeaderWhenOnCreateMode() {
        robot.launchActivity(AdaptableUi.Mode.CREATE)
                .checkIfHeaderIsVisible()
    }

    @Test
    fun headerShouldDisplayEmailAndPasswordTextWhenOnFirstStepOfCreateMode() {
        robot.launchActivity(AdaptableUi.Mode.CREATE)
                .checkIfHeaderShowsEmailAndPasswordText()
    }

    @Test
    fun headerShouldNotBeVisibleWhenOnEditMode() {
        robot.launchActivity(AdaptableUi.Mode.EDIT)
                .checkIfHeaderIsNotVisible()
    }

    @Test
    fun headerShouldNotBeVisibleWhenOnViewMode() {
        robot.launchActivity(AdaptableUi.Mode.VIEW)
                .checkIfHeaderIsNotVisible()
    }

    @Test
    fun shouldShowChangesNotSavedDialogueWhenBackButtonIsPressedInEditMode() {
        robot.launchActivity(AdaptableUi.Mode.EDIT)
                .pressBackButton()
        robot.checkIfShowsChangesNotSavedDialogue()
    }

    @Test
    fun vehiclesButtonShouldBeVisibleWhenOnViewMode() {
        robot.launchActivity(AdaptableUi.Mode.VIEW)
                .checkIfVehiclesButtonIsVisible()
    }

    @Test
    fun vehiclesButtonShouldNotBeVisibleWhenOnCreateMode() {
        robot.launchActivity(AdaptableUi.Mode.CREATE)
                .checkIfVehiclesButtonIsNotVisible()
    }

    @Test
    fun vehiclesButtonShouldNotBeVisibleWhenOnEditMode() {
        robot.launchActivity(AdaptableUi.Mode.EDIT)
                .checkIfVehiclesButtonIsNotVisible()
    }

    @Test
    fun shouldLaunchVehiclesActivityWhenClickingOnVehiclesButton() {
        robot.launchActivity(AdaptableUi.Mode.VIEW)
                .clickOnVehiclesButton()
                .checkIfLaunchesVehiclesActivity()
    }

}