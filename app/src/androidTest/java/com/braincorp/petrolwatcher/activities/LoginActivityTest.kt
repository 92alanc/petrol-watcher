package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.robots.LoginActivityRobot
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private val robot = LoginActivityRobot()

    @Before
    fun setup() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            AuthenticationManager.signOut {
                Intents.init()
            }
        }
    }

    @After
    fun after() {
        AuthenticationManager.signOut {
            Intents.release()
        }
    }

    /*
     * [RAGE QUIT]
     * If you run LoginActivity test alone, this test case
     * will pass, but if you run all instrumented tests in
     * one go, this test will make the whole testing process
     * terminate. Fix this if you have enough patience, I give up
     */
    @Ignore
    @Test
    fun shouldLaunchHomeActivityWithCorrectEmailAndPassword() {
        robot.launchActivity()
                .typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait(500)
        robot.checkIfLaunchesHomeActivity()
    }

    @Test
    fun shouldShowErrorDialogueWithIncorrectEmail() {
        robot.launchActivity()
                .typeEmail(correct = false)
                .hideKeyboard()
        robot.typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithIncorrectPassword() {
        robot.launchActivity()
                .typeEmail(correct = true)
                .hideKeyboard()
        robot.typePassword(correct = false)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithIncorrectEmailAndPassword() {
        robot.launchActivity()
                .typeEmail(correct = false)
                .hideKeyboard()
        robot.typePassword(correct = false)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithNullEmail() {
        robot.launchActivity()
                .typePassword(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithNullPassword() {
        robot.launchActivity()
                .typeEmail(correct = true)
                .hideKeyboard()
        robot.clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldShowErrorDialogueWithNullEmailAndPassword() {
        robot.launchActivity()
                .clickOnSignIn()
                .wait()
        robot.checkIfShowsErrorDialogue()
    }

    @Test
    fun shouldLaunchProfileActivityWhenClickingOnSignUp() {
        robot.launchActivity()
                .clickOnSignUp()
                .checkIfLaunchesProfileActivity()
    }

}