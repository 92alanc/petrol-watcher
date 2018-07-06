package com.braincorp.petrolwatcher.feature.auth.error

import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import com.braincorp.petrolwatcher.R
import org.junit.Assert.assertTrue

fun AuthenticationErrorActivityTest.authenticationError(
        func: AuthenticationErrorActivityRobot.() -> Unit): AuthenticationErrorActivityRobot {
    return AuthenticationErrorActivityRobot(rule).apply(func)
}

class AuthenticationErrorActivityRobot(private val rule: ActivityTestRule<AuthenticationErrorActivity>) {

    infix fun clickTryAgain(func: AuthenticationErrorResult.() -> Unit) {
        click {
            id(R.id.bt_try_again)
        }

        applyResult(func)
    }

    private fun applyResult(func: AuthenticationErrorResult.() -> Unit) {
        AuthenticationErrorResult(rule).apply(func)
    }

}

class AuthenticationErrorResult(private val rule: ActivityTestRule<AuthenticationErrorActivity>) {

    fun screenIsClosed() {
        assertTrue(rule.activity.isFinishing)
    }

}