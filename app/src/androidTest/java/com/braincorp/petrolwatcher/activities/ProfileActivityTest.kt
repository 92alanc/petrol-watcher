package com.braincorp.petrolwatcher.activities

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.robots.ProfileActivityRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {

    private val robot = ProfileActivityRobot()

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

}