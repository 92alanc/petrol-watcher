package com.braincorp.petrolwatcher.feature.auth.presenter

import android.util.Log
import com.braincorp.petrolwatcher.feature.auth.contract.EmailSignInContract
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

/**
 * The implementation of the presentation layer
 * of the e-mail sign in method
 *
 * @param view the view layer
 */
class EmailSignInActivityPresenter(private val view: EmailSignInContract.View) :
        EmailSignInContract.Presenter, OnSuccessListener<AuthResult>, OnFailureListener {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    /**
     * Signs in an existing e-mail and password
     * based account
     *
     * @param email the e-mail address
     * @param password the password
     */
    override fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this)
                .addOnFailureListener(this)
    }

    /**
     * Function called when the sign in process
     * is successful
     *
     * @param result the authentication result
     */
    override fun onSuccess(result: AuthResult?) {
        Log.d(TAG, "Login successful")
        // TODO: start map activity
    }

    /**
     * Function called when the sign in process
     * is not successful
     *
     * @param e the exception thrown
     */
    override fun onFailure(e: Exception) {
        Log.e(TAG, "Authentication failed", e)
        // TODO
    }

}