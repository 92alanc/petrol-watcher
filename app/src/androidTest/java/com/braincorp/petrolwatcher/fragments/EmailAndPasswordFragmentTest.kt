package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.robots.EmailAndPasswordFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAndPasswordFragmentTest {

    private val robot = EmailAndPasswordFragmentRobot()

    @Test
    fun emailFieldShouldBeEditableWhenOnCreateMode() {
        robot.launchFragment(UiMode.CREATE)
                .checkIfEmailFieldIsEditable()
    }

    @Test
    fun passwordFieldShouldBeVisibleWhenOnCreateMode() {
        robot.launchFragment(UiMode.CREATE)
                .checkIfPasswordFieldIsVisible()
    }

    @Test
    fun confirmPasswordFieldShouldBeVisibleWhenOnCreateMode() {
        robot.launchFragment(UiMode.CREATE)
                .checkIfConfirmPasswordFieldIsVisible()
    }

    @Test
    fun emailFieldShouldNotBeEditableWhenOnEditMode() {
        robot.launchFragment(UiMode.EDIT)
                .checkIfEmailFieldIsNotEditable()
    }

    @Test
    fun passwordFieldShouldNotBeVisibleWhenOnEditMode() {
        robot.launchFragment(UiMode.EDIT)
                .checkIfPasswordFieldIsNotVisible()
    }

    @Test
    fun confirmPasswordFieldShouldNotBeVisibleWhenOnEditMode() {
        robot.launchFragment(UiMode.EDIT)
                .checkIfConfirmPasswordFieldIsNotVisible()
    }

    @Test
    fun emailFieldShouldNotBeEditableWhenOnViewMode() {
        robot.launchFragment(UiMode.VIEW)
                .checkIfEmailFieldIsNotEditable()
    }

    @Test
    fun passwordFieldShouldNotBeVisibleWhenOnViewMode() {
        robot.launchFragment(UiMode.VIEW)
                .checkIfPasswordFieldIsNotVisible()
    }

    @Test
    fun confirmPasswordFieldShouldNotBeVisibleWhenOnViewMode() {
        robot.launchFragment(UiMode.VIEW)
                .checkIfConfirmPasswordFieldIsNotVisible()
    }

}