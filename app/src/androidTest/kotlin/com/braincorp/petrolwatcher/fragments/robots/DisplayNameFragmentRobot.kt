package com.braincorp.petrolwatcher.fragments.robots

import android.content.Intent
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.fragments.DisplayNameFragment
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.robots.BaseRobot
import org.junit.Rule

class DisplayNameFragmentRobot : BaseRobot() {

    @Rule
    private val rule = FragmentTestRule.create(DisplayNameFragment::class.java,
            false, false)

    fun launchFragment(uiMode: AdaptableUi.Mode): DisplayNameFragmentRobot {
        rule.launchActivity(Intent())
        val fragment = DisplayNameFragment.newInstance(uiMode)
        rule.launchFragment(fragment)
        return this
    }

    fun checkIfDisplayNameFieldIsEditable(): DisplayNameFragmentRobot {
        checkIfVisible(R.id.editTextDisplayName)
                .checkIfNotVisible(R.id.textViewDisplayName)
        return this
    }

    fun checkIfDisplayNameFieldIsNotEditable(): DisplayNameFragmentRobot {
        checkIfVisible(R.id.textViewDisplayName)
                .checkIfNotVisible(R.id.editTextDisplayName)
        return this
    }

}