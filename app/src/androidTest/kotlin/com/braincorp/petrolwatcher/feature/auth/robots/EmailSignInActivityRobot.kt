package com.braincorp.petrolwatcher.feature.auth.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.EmailAndPasswordSignUpActivity
import com.braincorp.petrolwatcher.feature.stations.MapActivity

fun emailSignIn(func: EmailSignInActivityRobot.() -> Unit) = EmailSignInActivityRobot().apply(func)

class EmailSignInActivityRobot {

    fun typeEmail(email: String) {
        typeText(email) {
            id(R.id.edt_email)
        }
    }

    fun typePassword(password: String) {
        typeText(password) {
            id(R.id.edt_password)
        }
    }

    infix fun clickSignIn(func: EmailSignInResult.() -> Unit) {
        click {
            id(R.id.bt_sign_in)
        }

        applyResult(func)
    }

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

    fun redirectToEmailAndPasswordSignUpActivity() {
        sentIntent {
            className(EmailAndPasswordSignUpActivity::class.java.name)
        }
    }

    fun redirectToMapActivity() {
        sentIntent {
            className(MapActivity::class.java.name)
        }
    }

    fun showErrorDialogue() {
        displayed {
            text(R.string.error_incorrect_email_password)
        }
    }

}