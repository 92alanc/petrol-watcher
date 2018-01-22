package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.filters.FlakyTest
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.robots.action.LoginActivityActionRobot
import com.braincorp.petrolwatcher.robots.assertion.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private companion object {
        const val DEFAULT_TIMEOUT = 5000L
    }

    private val robot = LoginActivityActionRobot()

    @Before
    fun setup() {
        if (AuthenticationManager.isSignedIn()) {
            /*
             * If the test user is signed in there's
             * nothing that can be automatically done
             * in order to sign it out. Not even calling
             * AuthenticationManager.signOut() will do,
             * so the best thing here is to abort the
             * whole test suite and start it again.
             */
            System.exit(1)
        }
        robot.launchActivity()
    }

    @After
    fun after() {
        AuthenticationManager.signOut()
        robot.wait(2000)
        Intents.release()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldLaunchHomeActivityWithCorrectEmailAndPassword() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait(2000)
        checkIfLaunchesHomeActivity()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithIncorrectEmail() {
        robot.typeEmail(correct = false)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithIncorrectPassword() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = false)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithIncorrectEmailAndPassword() {
        robot.typeEmail(correct = false)
                .hideKeyboard()
        robot.typePassword(correct = false)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithNullEmail() {
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithNullPassword() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @FlakyTest(detail = "Sometimes there's not enough time to receive the authentication server response")
    @Test(timeout = DEFAULT_TIMEOUT)
    fun shouldShowErrorDialogueWithNullEmailAndPassword() {
        robot.clickOnSignIn()
                .wait()
        checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldLaunchProfileActivityWhenClickingOnSignUp() {
        robot.clickOnSignUp()
        checkIfLaunchesProfileActivity()
    }

    @Test
    fun shouldKeepEmailTextAfterRotatingDevice() {
        robot.typeEmail(correct = true)
                .hideKeyboard()
                .rotateDeviceClockwise()
        checkIfEmailTextIsDisplayed(correct = true)
        robot.restoreDeviceOrientation()
    }

    @Test
    fun shouldKeepPasswordTextAfterRotatingDevice() {
        robot.typePassword(correct = true)
                .hideKeyboard()
                .rotateDeviceClockwise()
        checkIfPasswordTextIsDisplayed(correct = true)
        robot.restoreDeviceOrientation()
    }

}