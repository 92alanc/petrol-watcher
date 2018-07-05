package com.braincorp.petrolwatcher.feature.auth.presenter

import android.text.TextUtils.isEmpty
import com.alancamargo.validationchain.ValidationChain
import com.alancamargo.validationchain.model.Validation
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract

/**
 * The implementation of the presentation layer of the
 * e-mail and password sign up method
 *
 * @param view the view layer
 */
class EmailAndPasswordSignUpPresenter(private val view: EmailAndPasswordSignUpContract.View) :
        EmailAndPasswordSignUpContract.Presenter {

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

        val emailNotEmpty = Validation(successCondition = !isEmpty(email),
                onFailureAction = view::showEmptyEmailError)

        val passwordNotEmpty = Validation(successCondition = !isEmpty(password),
                onFailureAction = view::showEmptyPasswordError)

        val confirmationNotEmpty = Validation(successCondition = !isEmpty(confirmation),
                onFailureAction = view::showEmptyConfirmationError)

        ValidationChain().add(confirmationMatches)
                .add(emailNotEmpty)
                .add(passwordNotEmpty)
                .add(confirmationNotEmpty)
                .addSuccessListener(object: ValidationChain.OnSuccessListener {
                    override fun onSuccess() {
                        view.showProfile()
                    }
                }).run()
    }

}