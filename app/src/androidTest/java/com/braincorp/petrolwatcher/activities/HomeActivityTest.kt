package com.braincorp.petrolwatcher.activities

import android.support.test.espresso.intent.Intents
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.robots.HomeActivityRobot
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    private val robot = HomeActivityRobot()

    @After
    fun after() {
        Intents.release()
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
        robot.launchActivity()
                .openNavigationBar()
                .checkIfNavigationBarIsOpen()
                .clickOnSignOut()
                .clickOnYesDialogueButton()
                .checkIfLaunchesLoginActivity()
    }

    @Test
    fun shouldLaunchProfileActivityWhenClickingOnProfile() {
        robot.launchActivity()
                .openNavigationBar()
                .checkIfNavigationBarIsOpen()
                .clickOnProfile()
                .checkIfLaunchesProfileActivity()
    }

}