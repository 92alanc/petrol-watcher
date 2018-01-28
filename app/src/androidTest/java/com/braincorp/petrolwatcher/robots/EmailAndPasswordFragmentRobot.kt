package com.braincorp.petrolwatcher.robots

import android.content.Intent
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.fragments.EmailAndPasswordFragment
import com.braincorp.petrolwatcher.model.UiMode
import org.junit.Rule

class EmailAndPasswordFragmentRobot : BaseRobot() {

    @Rule
    private val rule = FragmentTestRule.create(EmailAndPasswordFragment::class.java,
            false, false)

    fun launchFragment(uiMode: UiMode): EmailAndPasswordFragmentRobot {
        rule.launchActivity(Intent())
        val fragment = EmailAndPasswordFragment.newInstance(uiMode)
        rule.launchFragment(fragment)
        return this
    }

    fun checkIfEmailFieldIsEditable(): EmailAndPasswordFragmentRobot {
        checkIfVisible(R.id.editTextEmail)
                .checkIfNotVisible(R.id.textViewEmail)
        return this
    }

    fun checkIfEmailFieldIsNotEditable(): EmailAndPasswordFragmentRobot {
        checkIfVisible(R.id.textViewEmail)
                .checkIfNotVisible(R.id.editTextEmail)
        return this
    }

    fun checkIfPasswordFieldIsVisible(): EmailAndPasswordFragmentRobot {
        checkIfVisible(R.id.editTextPassword)
        return this
    }

    fun checkIfPasswordFieldIsNotVisible(): EmailAndPasswordFragmentRobot {
        checkIfNotVisible(R.id.editTextPassword)
        return this
    }

    fun checkIfConfirmPasswordFieldIsVisible(): EmailAndPasswordFragmentRobot {
        checkIfVisible(R.id.editTextConfirmPassword)
        return this
    }

    fun checkIfConfirmPasswordFieldIsNotVisible(): EmailAndPasswordFragmentRobot {
        checkIfNotVisible(R.id.editTextConfirmPassword)
        return this
    }

}