package com.braincorp.petrolwatcher.feature.auth.utils

import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

/**
 * Gets the account that's currently signed in
 *
 * @return the active account (if existent) or null
 */
fun getActiveAccount(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

/**
 * Sets the profile picture and the display name to the
 * currently logged in user
 *
 * @param profilePictureUri the profile picture URI
 * @param displayName the display name
 * @param onSuccessListener the success listener
 * @param onFailureListener the failure listener
 */
fun setProfilePictureAndDisplayName(profilePictureUri: Uri?, displayName: String,
                                    onSuccessListener: OnSuccessListener<Void>,
                                    onFailureListener: OnFailureListener) {
    val user = FirebaseAuth.getInstance().currentUser
    user?.updateProfile(UserProfileChangeRequest.Builder()
            .setPhotoUri(profilePictureUri)
            .setDisplayName(displayName)
            .build())
            ?.addOnSuccessListener(onSuccessListener)
            ?.addOnFailureListener(onFailureListener)
}
