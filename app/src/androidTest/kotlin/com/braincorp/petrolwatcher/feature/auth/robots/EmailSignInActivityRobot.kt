package com.braincorp.petrolwatcher.feature.auth.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.NewAccountActivity

fun emailSignIn(func: EmailSignInActivityRobot.() -> Unit) = EmailSignInActivityRobot().apply(func)

class EmailSignInActivityRobot {

    infix fun clickSignUp(func: EmailSignInResult.() -> Unit) {
        click {
            id(R.id.bt_sign_up)
        }

        applyResult(func)
    }

    private fun applyResult(func: EmailSignInResult.() -> Unit) {
        EmailSignInResult().apply(func)
    }

}

class EmailSignInResult {

    fun redirectToNewAccountActivity() {
        sentIntent {
            className(NewAccountActivity::class.java.name)
        }
    }

}