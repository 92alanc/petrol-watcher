package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.fragments.robots.ImagePickerFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagePickerFragmentTest {

    private val robot = ImagePickerFragmentRobot()

    @Test
    fun imageViewShouldBeClickableWhenOnCreateMode() {
        robot.launchFragment(UiMode.CREATE)
                .checkIfImagePickerIsClickable()
    }

    @Test
    fun imageViewShouldBeClickableWhenOnEditMode() {
        robot.launchFragment(UiMode.EDIT)
                .checkIfImagePickerIsClickable()
    }

    @Test
    fun imageViewShouldNotBeClickableWhenOnViewMode() {
        robot.launchFragment(UiMode.VIEW)
                .checkIfImagePickerIsNotClickable()
    }

    @Test
    fun shouldShowDialogueWhenClickingOnImageView() {
        robot.launchFragment(UiMode.CREATE)
                .clickOnImageView()
                .checkIfShowsDialogue()
    }

}