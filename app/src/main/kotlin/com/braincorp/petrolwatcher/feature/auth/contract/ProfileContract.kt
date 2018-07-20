package com.braincorp.petrolwatcher.feature.auth.contract

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.base.BaseContract

interface ProfileContract {
    /**
     * The view layer of the profile screen
     */
    interface View : BaseContract.View<Presenter> {
        /**
         * Shows an error dialogue
         *
         * @param messageId the error message ID
         */
        fun showErrorDialogue(@StringRes messageId: Int)

        /**
         * Shows an error dialogue
         *
         * @param message the message
         */
        fun showErrorDialogue(message: String)

        /**
         * Shows the map activity
         */
        fun showMap()
    }

    /**
     * The presentation layer of the profile screen
     */
    interface Presenter : BaseContract.Presenter {
        /**
         * Opens the camera
         *
         * @param activity the activity where the
         * camera will be opened from
         * @param requestCode the request code
         */
        fun openCamera(activity: AppCompatActivity, requestCode: Int)

        /**
         * Opens the gallery
         *
         * @param activity the activity where the
         * gallery will be opened from
         * @param requestCode the request code
         */
        fun openGallery(activity: AppCompatActivity, requestCode: Int)

        /**
         * Saves the profile
         *
         * @param picture the profile picture
         * @param displayName the display name
         * @param context the Android context
         */
        fun saveProfile(picture: Bitmap?, displayName: String, context: Context)

        /**
         * Gets an image URI from a camera intent
         *
         * @param intent the intent
         * @param context the Android context
         *
         * @return the image URI
         */
        fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri?

        /**
         * Gets an image URI from a gallery intent
         *
         * @param intent the intent
         *
         * @return the image URI
         */
        fun getImageUriFromGalleryIntent(intent: Intent?): Uri?
    }
}