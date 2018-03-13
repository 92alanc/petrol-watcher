package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.fragments.robots.EmailAndPasswordFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordFragmentTest {

    private val robot = EmailAndPasswordFragmentRobot()

    @Test
    fun emailFieldShouldBeEditableWhenOnCreateMode() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .checkIfEmailFieldIsEditable()
    }

    @Test
    fun passwordFieldShouldBeVisibleWhenOnCreateMode() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .checkIfPasswordFieldIsVisible()
    }

    @Test
    fun confirmPasswordFieldShouldBeVisibleWhenOnCreateMode() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .checkIfConfirmPasswordFieldIsVisible()
    }

    @Test
    fun emailFieldShouldNotBeEditableWhenOnEditMode() {
        robot.launchFragment(AdaptableUi.Mode.EDIT)
                .checkIfEmailFieldIsNotEditable()
    }

    @Test
    fun passwordFieldShouldNotBeVisibleWhenOnEditMode() {
        robot.launchFragment(AdaptableUi.Mode.EDIT)
                .checkIfPasswordFieldIsNotVisible()
    }

    @Test
    fun confirmPasswordFieldShouldNotBeVisibleWhenOnEditMode() {
        robot.launchFragment(AdaptableUi.Mode.EDIT)
                .checkIfConfirmPasswordFieldIsNotVisible()
    }

    @Test
    fun emailFieldShouldNotBeEditableWhenOnViewMode() {
        robot.launchFragment(AdaptableUi.Mode.VIEW)
                .checkIfEmailFieldIsNotEditable()
    }

    @Test
    fun passwordFieldShouldNotBeVisibleWhenOnViewMode() {
        robot.launchFragment(AdaptableUi.Mode.VIEW)
                .checkIfPasswordFieldIsNotVisible()
    }

    @Test
    fun confirmPasswordFieldShouldNotBeVisibleWhenOnViewMode() {
        robot.launchFragment(AdaptableUi.Mode.VIEW)
                .checkIfConfirmPasswordFieldIsNotVisible()
    }

}