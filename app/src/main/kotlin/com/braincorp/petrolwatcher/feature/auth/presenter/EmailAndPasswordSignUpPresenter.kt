package com.braincorp.petrolwatcher.feature.auth.presenter

import android.text.TextUtils.isEmpty
import com.alancamargo.validationchain.ValidationChain
import com.alancamargo.validationchain.model.Validation
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract

class EmailAndPasswordSignUpPresenter(private val view: EmailAndPasswordSignUpContract.View) :
        EmailAndPasswordSignUpContract.Presenter {

    override fun validateCredentials(email: String, password: String, confirmation: String) {
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