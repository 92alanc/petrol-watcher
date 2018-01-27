package com.braincorp.petrolwatcher.robots

import android.content.Intent
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.TestActivity
import com.braincorp.petrolwatcher.fragments.DisplayNameFragment
import com.braincorp.petrolwatcher.model.UiMode
import org.junit.Rule

class DisplayNameFragmentRobot : BaseRobot() {

    @Rule
    private val rule = FragmentTestRule(TestActivity::class.java,
            DisplayNameFragment::class.java,
            false, false)

    fun launchFragment(uiMode: UiMode): DisplayNameFragmentRobot {
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