package com.braincorp.petrolwatcher.authentication

import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object AuthenticationManager {

    private val AUTH = FirebaseAuth.getInstance()

    val USER = AUTH.currentUser

    fun createUser(email: String, password: String,
                   displayName: String, photoUri: Uri? = null,
                   onEmailVerificationSentAction: () -> Unit) {
        AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener({
                    it.result.user.updateProfile(UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName)
                            .setPhotoUri(photoUri)
                            .build())
                    it.result.user.sendEmailVerification()
                            .addOnCompleteListener({
                                onEmailVerificationSentAction()
                            })
                })
    }

    fun signIn(email: String, password: String,
               onCompleteListener: OnCompleteListener<AuthResult>? = null) {
        if (onCompleteListener != null) {
            AUTH.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(onCompleteListener)
        } else {
            AUTH.signInWithEmailAndPassword(email, password)
        }
    }

    fun signOut() {
        AUTH.signOut()
    }

    fun deleteUser() {
        USER?.delete()
        signOut()
    }

    fun isSignedIn(): Boolean = USER != null

    fun resetPassword(email: String, onCompleteListener: OnCompleteListener<Void>) {
        AUTH.sendPasswordResetEmail(email)
                .addOnCompleteListener(onCompleteListener)
    }

}