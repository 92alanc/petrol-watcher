package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.fragments.robots.DisplayNameFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplayNameFragmentTest {

    private val robot = DisplayNameFragmentRobot()

    @Test
    fun displayNameFieldShouldNotBeEditableWhenOnViewMode() {
        robot.launchFragment(AdaptableUi.Mode.VIEW)
                .checkIfDisplayNameFieldIsNotEditable()
    }

    @Test
    fun displayNameFieldShouldBeEditableWhenOnCreateMode() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .checkIfDisplayNameFieldIsEditable()
    }

    @Test
    fun displayNameFieldShouldBeEditableWhenOnEditMode() {
        robot.launchFragment(AdaptableUi.Mode.EDIT)
                .checkIfDisplayNameFieldIsEditable()
    }

}