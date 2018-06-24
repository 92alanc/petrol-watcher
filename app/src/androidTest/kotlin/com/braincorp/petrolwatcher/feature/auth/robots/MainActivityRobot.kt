package com.braincorp.petrolwatcher.feature.auth.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.EmailSignInActivity

fun mainActivity(func: MainActivityRobot.() -> Unit) = MainActivityRobot().apply(func)

class MainActivityRobot {

    infix fun clickOnSignInWithEmail(func: MainActivityResult.() -> Unit) {
        click {
            id(R.id.bt_sign_in_email)
        }

        applyResult(func)
    }

    private fun applyResult(func: MainActivityResult.() -> Unit) {
        MainActivityResult().apply(func)
    }

}

class MainActivityResult {

    fun redirectToEmailSignInActivity() {
        sentIntent {
            className(EmailSignInActivity::class.java.name)
        }
    }

}