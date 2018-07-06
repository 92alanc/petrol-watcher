package com.braincorp.petrolwatcher.feature.auth.presenter

import android.text.TextUtils.isEmpty
import android.util.Log
import com.alancamargo.validationchain.ValidationChain
import com.alancamargo.validationchain.model.Validation
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract
import com.google.firebase.auth.FirebaseAuth

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
                .run { createFirebaseAccount(email, password) }
    }

    private fun createFirebaseAccount(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { view.showProfile() }
                .addOnFailureListener { Log.e("ALAN", "Failure", it) } // TODO: handle sign up failure
    }

}