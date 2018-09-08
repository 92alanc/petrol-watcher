package com.braincorp.petrolwatcher.feature.auth.authenticator

import android.net.Uri

/**
 * A listener for user data
 */
interface OnUserDataFoundListener {

    /**
     * Function triggered when user data is found
     *
     * @param displayName the display name
     * @param profilePictureUri the profile picture URI
     */
    fun onUserDataFound(displayName: String?, profilePictureUri: Uri?)

}