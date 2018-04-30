package com.braincorp.petrolwatcher.feature.users.activities.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import com.braincorp.petrolwatcher.R

fun profile(func: () -> Unit) = ProfileActivityRobot().apply { func() }

class ProfileActivityRobot {

    fun typeEmail(email: String) {
        typeText(email) {
            id(R.id.editTextEmail)
        }
    }

    fun typePassword(password: String) {
        typeText(password) {
            id(R.id.editTextPassword)
        }
    }

    fun typePasswordConfirmation(confirmation: String) {
        typeText(confirmation) {
            id(R.id.editTextConfirmPassword)
        }
    }

    infix fun clickOnNext(func: ProfileResult.() -> Unit) {
        click {
            id(R.id.fabProfile)
        }
        applyResult(func)
    }

    private fun applyResult(func: ProfileResult.() -> Unit) {
        ProfileResult().apply { func() }
    }

    class ProfileResult {

        

    }

}