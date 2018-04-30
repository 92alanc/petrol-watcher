package com.braincorp.petrolwatcher.feature.users.activities

import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.map.activities.MapActivity

fun login(func: LoginActivityRobot.() -> Unit) = LoginActivityRobot().apply {
    func()
}

class LoginActivityRobot {

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

    infix fun clickOnSignIn(func: LoginResult.() -> Unit) {
        click {
            id(R.id.buttonSignIn)
        }
        applyResult(func)
    }

    infix fun clickOnSignUp(func: LoginResult.() -> Unit) {
        click {
            id(R.id.buttonSignUp)
        }
        applyResult(func)
    }

    private fun applyResult(func: LoginResult.() -> Unit) {
        LoginResult().apply { func() }
    }

    class LoginResult {

        fun loginIsSuccessful() {
            intended(hasComponent(MapActivity::class.java.name))
        }

        fun redirectsToSignUpScreen() {
            intended(hasComponent(ProfileActivity::class.java.name))
        }

    }

}