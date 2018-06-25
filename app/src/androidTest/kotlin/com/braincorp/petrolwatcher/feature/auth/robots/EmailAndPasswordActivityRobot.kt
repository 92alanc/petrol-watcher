package com.braincorp.petrolwatcher.feature.auth.robots

import android.support.annotation.StringRes
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.matcher.ViewMatchers.hasErrorText
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import com.braincorp.petrolwatcher.R

fun emailAndPassword(func: EmailAndPasswordActivityRobot.() -> Unit) =
        EmailAndPasswordActivityRobot().apply(func)

class EmailAndPasswordActivityRobot {

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

    fun typeConfirmation(confirmation: String) {
        typeText(confirmation) {
            id(R.id.edt_password_confirmation)
        }
    }

    infix fun clickNext(func: EmailAndPasswordResult.() -> Unit) {
        click {
            id(R.id.fab_next)
        }

        applyResult(func)
    }

    private fun applyResult(func: EmailAndPasswordResult.() -> Unit) =
            EmailAndPasswordResult().apply(func)

}

class EmailAndPasswordResult {

    fun redirectToProfileActivity() {
        // TODO: validate
    }

    fun showEmptyEmailError() {
        showError(R.string.error_empty_email)
    }

    fun showEmptyPasswordError() {
        showError(R.string.error_empty_password)
    }

    fun showEmptyConfirmationError() {
        showError(R.string.error_empty_confirmation)
    }

    fun showPasswordAndConfirmationMismatchError() {
        showError(R.string.error_password_and_confirmation_dont_match)
    }

    private fun showError(@StringRes errorTextRes: Int) {
        val context = InstrumentationRegistry.getTargetContext()
        val errorText = context.getString(errorTextRes)
        displayed {
            custom(hasErrorText(errorText))
        }
    }

}