package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.robots.action.LoginActivityActionRobot
import com.braincorp.petrolwatcher.robots.assertion.checkIfLaunchesHomeActivity
import com.braincorp.petrolwatcher.robots.assertion.checkIfShowsErrorDialogue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private val robot = LoginActivityActionRobot()

    @Before
    fun setup() {
        if (AuthenticationManager.isSignedIn()) {
            AuthenticationManager.signOut()
            robot.wait(2000)
        }
        robot.launchActivity()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun shouldLaunchHomeActivityWithCorrectEmailAndPassword() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait(1000)
        checkIfLaunchesHomeActivity()
        AuthenticationManager.signOut()
    }

    @Test
    fun shouldShowErrorDialogueWithIncorrectEmail() {
        robot.typeEmail(correct = false)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithIncorrectPassword() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = false)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

}