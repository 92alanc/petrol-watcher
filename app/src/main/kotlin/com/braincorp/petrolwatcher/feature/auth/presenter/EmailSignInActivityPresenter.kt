package com.braincorp.petrolwatcher.feature.auth.presenter

import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.contract.EmailSignInContract
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import java.lang.Exception

/**
 * The implementation of the presentation layer
 * of the e-mail sign in method
 *
 * @param view the view layer
 */
open class EmailSignInActivityPresenter(private val view: EmailSignInContract.View,
                                        var authenticator: Authenticator) :
        EmailSignInContract.Presenter, OnSuccessListener<AuthResult>, OnFailureListener {

    /**
     * Signs in an existing e-mail and password
     * based account
     *
     * @param email the e-mail address
     * @param password the password
     */
    override fun signIn(email: String, password: String) {
        authenticator.signIn(email, password, this, this)
    }

    /**
     * Function called when the sign in process
     * is successful
     *
     * @param result the authentication result
     */
    override fun onSuccess(result: AuthResult?) {
        view.showMap()
    }

    /**
     * Function called when the sign in process
     * is not successful
     *
     * @param e the exception thrown
     */
    override fun onFailure(e: Exception) {
        view.showErrorDialogue()
    }

}