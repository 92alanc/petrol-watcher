package com.braincorp.petrolwatcher.feature.auth.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.AuthenticationErrorActivity
import com.braincorp.petrolwatcher.feature.auth.EmailSignInActivity

fun mainActivity(func: MainActivityRobot.() -> Unit) = MainActivityRobot().apply(func)

class MainActivityRobot {

    infix fun clickSignInWithGoogle(func: MainActivityResult.() -> Unit) {
        click {
            id(R.id.bt_sign_in_google)
        }

        applyResult(func)
    }

    infix fun clickSignInWithEmail(func: MainActivityResult.() -> Unit) {
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

    fun redirectToMapActivity() {
        // TODO: implement
    }

    fun showErrorScreen() {
        sentIntent {
            className(AuthenticationErrorActivity::class.java.name)
        }
    }

}