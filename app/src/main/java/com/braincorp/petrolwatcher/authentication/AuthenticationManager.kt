package com.braincorp.petrolwatcher.authentication

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

object AuthenticationManager {

    private val AUTH = FirebaseAuth.getInstance()

    val USER = AUTH.currentUser

    fun createUser(email: String, password: String,
                   onCompleteListener: OnCompleteListener<AuthResult>) {
        AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
    }

    fun signIn(email: String, password: String,
               func: () -> Unit) {
        AUTH.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener({
                    it.result.user.sendEmailVerification()
                    apply { func() }
                })
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