package com.braincorp.petrolwatcher.fragments

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.fragments.robots.ImagePickerFragmentRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagePickerFragmentTest {

    private val robot = ImagePickerFragmentRobot()

    @Test
    fun imageViewShouldBeClickableWhenOnCreateMode() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .checkIfImagePickerIsClickable()
    }

    @Test
    fun imageViewShouldBeClickableWhenOnEditMode() {
        robot.launchFragment(AdaptableUi.Mode.EDIT)
                .checkIfImagePickerIsClickable()
    }

    @Test
    fun imageViewShouldNotBeClickableWhenOnViewMode() {
        robot.launchFragment(AdaptableUi.Mode.VIEW)
                .checkIfImagePickerIsNotClickable()
    }

    @Test
    fun shouldShowDialogueWhenClickingOnImageView() {
        robot.launchFragment(AdaptableUi.Mode.CREATE)
                .clickOnImageView()
                .checkIfShowsDialogue()
    }

}