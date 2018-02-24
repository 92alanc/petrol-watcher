package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.activities.robots.MapActivityRobot
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapActivityTest {

    private val robot = MapActivityRobot()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun after() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            AuthenticationManager.signOut {
                Intents.release()
            }
        } else Intents.release()
    }

    @Test
    fun shouldShowDialogueWhenClickingOnSignOut() {
        robot.launchActivity()
                .openNavigationBar()
                .checkIfNavigationBarIsOpen()
                .clickOnSignOut()
                .checkIfShowsQuestionDialogue()
    }

    @Test
    fun shouldLaunchLoginActivityWhenClickingYesInSignOutDialogue() {
        val email = "alcam.ukdev@gmail.com"
        val password = "abcd1234"
        AuthenticationManager.signIn(email, password, onSuccessAction = {
            robot.launchActivity()
                    .openNavigationBar()
                    .checkIfNavigationBarIsOpen()
                    .clickOnSignOut()
                    .clickOnYesDialogueButton()
                    .checkIfLaunchesLoginActivity()
        }, onFailureAction = {
            fail()
        })
    }

    @Test
    fun shouldLaunchProfileActivityWhenClickingOnProfile() {
        robot.launchActivity()
                .openNavigationBar()
                .checkIfNavigationBarIsOpen()
                .clickOnProfile()
                .checkIfLaunchesProfileActivity()
    }

    // For some stupid reason this test terminates the whole testing process
    @Ignore
    @Test
    fun shouldLaunchPetrolStationsActivityWhenClickingOnStationsNearby() {
        robot.launchActivity()
                .openNavigationBar()
                .clickOnStationsNearby()
                .checkIfLaunchesPetrolStationsActivity()
    }

}