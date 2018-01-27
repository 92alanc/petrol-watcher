package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.robots.DisplayNameFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplayNameFragmentTest {

    private val robot = DisplayNameFragmentRobot()

    @Test
    fun displayNameFieldShouldNotBeEditableWhenOnViewMode() {
        robot.launchFragment(UiMode.VIEW)
                .checkIfDisplayNameFieldIsNotEditable()
    }

    @Test
    fun displayNameFieldShouldBeEditableWhenOnCreateMode() {
        robot.launchFragment(UiMode.CREATE)
                .checkIfDisplayNameFieldIsEditable()
    }

    @Test
    fun displayNameFieldShouldBeEditableWhenOnEditMode() {
        robot.launchFragment(UiMode.EDIT)
                .checkIfDisplayNameFieldIsEditable()
    }

}