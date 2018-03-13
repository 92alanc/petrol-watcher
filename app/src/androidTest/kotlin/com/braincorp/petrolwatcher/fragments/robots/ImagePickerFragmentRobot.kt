package com.braincorp.petrolwatcher.fragments.robots

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.fragments.ImagePickerFragment
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.robots.BaseRobot
import org.hamcrest.CoreMatchers.not
import org.junit.Rule

class ImagePickerFragmentRobot : BaseRobot() {

    @Rule
    private val rule = FragmentTestRule.create(ImagePickerFragment::class.java,
            false, false)

    fun launchFragment(uiMode: AdaptableUi.Mode): ImagePickerFragmentRobot {
        rule.launchActivity(Intent())
        val fragment = ImagePickerFragment.newInstance(uiMode)
        rule.launchFragment(fragment)
        return this
    }

    fun checkIfImagePickerIsClickable(): ImagePickerFragmentRobot {
        imageView().check(matches(isClickable()))
        return this
    }

    fun checkIfImagePickerIsNotClickable(): ImagePickerFragmentRobot {
        imageView().check(matches(not(isClickable())))
        return this
    }

    fun clickOnImageView(): ImagePickerFragmentRobot {
        click(R.id.imageViewProfile)
        return this
    }

    fun checkIfShowsDialogue(): ImagePickerFragmentRobot {
        onView(withText(R.string.pick_image_from)).check(matches(isDisplayed()))
        return this
    }

    private fun imageView(): ViewInteraction = onView(withId(R.id.imageViewProfile))

}