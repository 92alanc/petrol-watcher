package com.braincorp.petrolwatcher.feature.auth.presenter

import android.text.TextUtils.isEmpty
import com.alancamargo.validationchain.ValidationChain
import com.alancamargo.validationchain.model.Validation
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import java.lang.Exception

/**
 * The implementation of the presentation layer of the
 * e-mail and password sign up method
 *
 * @param view the view layer
 */
open class EmailAndPasswordSignUpPresenter(private val view: EmailAndPasswordSignUpContract.View,
                                           var authenticator: Authenticator) :
        EmailAndPasswordSignUpContract.Presenter, OnSuccessListener<AuthResult>, OnFailureListener {

    private companion object {
        const val EMAIL_REGEX = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
        const val MIN_PASSWORD_LENGTH = 6
    }

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param confirmation the password confirmation (must
     *                     be equal to password)
     */
    override fun createAccount(email: String, password: String, confirmation: String) {
        val confirmationMatches = Validation(successCondition = password == confirmation,
                onFailureAction = view::showPasswordNotMatchingError)

        val confirmationNotEmpty = Validation(successCondition = !isEmpty(confirmation),
                onFailureAction = view::showEmptyConfirmationError)

        val emailFormat = Validation(successCondition = email.matches(EMAIL_REGEX.toRegex()),
                onFailureAction = view::showEmailFormatError)

        val passwordLength = Validation(successCondition = password.length >= MIN_PASSWORD_LENGTH,
                onFailureAction = view::showPasswordLengthWarning)

        ValidationChain().add(confirmationMatches)
                .add(confirmationNotEmpty)
                .add(emailFormat)
                .add(passwordLength)
                .run {
                    authenticator.signUp(email, password, this, this)
                }
    }

    override fun onSuccess(result: AuthResult?) {
        view.showProfile()
    }

    override fun onFailure(e: Exception) {
        view.showBackendError()
    }

}