package com.braincorp.petrolwatcher.feature.auth.robots

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.AuthenticationErrorActivity
import com.braincorp.petrolwatcher.feature.auth.AuthenticationErrorActivityTest
import org.junit.Assert.assertTrue

fun AuthenticationErrorActivityTest.authenticationError(
        func: AuthenticationErrorActivityRobot.() -> Unit): AuthenticationErrorActivityRobot {
    return AuthenticationErrorActivityRobot(rule).apply(func)
}

class AuthenticationErrorActivityRobot(private val rule: ActivityTestRule<AuthenticationErrorActivity>) {

    fun imageIs(@DrawableRes resId: Int) {
        displayed {
            allOf {
                id(R.id.img_error)
                image(resId)
            }
        }
    }

    fun messageIs(@StringRes resId: Int) {
        displayed {
            allOf {
                id(R.id.txt_error_description)
                text(resId)
            }
        }
    }

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