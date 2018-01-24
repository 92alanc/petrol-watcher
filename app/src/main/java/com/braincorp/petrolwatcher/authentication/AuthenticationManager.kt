package com.braincorp.petrolwatcher.authentication

import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

object AuthenticationManager {

    fun createUser(email: String, password: String,
                   onUserCreatedAction: (Task<AuthResult>) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onUserCreatedAction)
    }

    fun setDisplayNameAndProfilePicture(user: FirebaseUser?,
                                        displayName: String, profilePicture: Uri?,
                                        onSuccessAction: () -> Unit,
                                        onFailureAction: () -> Unit) {
        if (user == null) {
            FirebaseAuth.getInstance().currentUser
                    ?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(displayName)
                            .setPhotoUri(profilePicture)
                            .build())?.addOnCompleteListener({
                if (it.isSuccessful) onSuccessAction()
                else onFailureAction()
            })
        } else {
            user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(displayName)
                    .setPhotoUri(profilePicture)
                    .build()).addOnCompleteListener({
                if (it.isSuccessful) onSuccessAction()
                else onFailureAction()
            })
        }
    }

    fun signIn(email: String, password: String,
               onSuccessAction: (AuthResult) -> Unit,
               onFailureAction: () -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener({
                    Runnable {
                        while (true) {
                            if (it.user != null) {
                                onSuccessAction(it)
                                break
                            }
                        }
                    }.run()
                })
                .addOnFailureListener({
                    onFailureAction()
                })
    }

    fun signOut(onSuccessAction: () -> Unit) {
        FirebaseAuth.getInstance().signOut()
        Runnable {
            while (true) {
                if (!isSignedIn()) {
                    onSuccessAction()
                    break
                }
            }
        }.run()
    }

    fun deleteUser(onSuccessAction: () -> Unit) {
        FirebaseAuth.getInstance()
                .currentUser
                ?.delete()
        Runnable {
            while (true) {
                if (!isSignedIn()) {
                    onSuccessAction()
                    break
                }
            }
        }.run()
    }

    fun isSignedIn(): Boolean = FirebaseAuth.getInstance().currentUser != null

    fun resetPassword(email: String, onCompleteListener: OnCompleteListener<Void>) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(onCompleteListener)
    }

}