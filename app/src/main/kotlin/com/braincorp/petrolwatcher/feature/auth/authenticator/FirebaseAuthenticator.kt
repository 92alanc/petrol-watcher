package com.braincorp.petrolwatcher.feature.auth.authenticator

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * A Firebase authentication wrapper
 */
class FirebaseAuthenticator : Authenticator {

    /**
     * Signs in with e-mail and password
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signIn(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
    }

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signUp(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
    }

}