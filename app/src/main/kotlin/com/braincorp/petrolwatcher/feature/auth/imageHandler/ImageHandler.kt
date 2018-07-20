package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity

interface ImageHandler {

    /**
     * Gets the camera intent
     *
     * @param activity the activity where the camera will
     *                 be opened from
     * @param requestCode the request code for the camera
     *                    permission
     *
     * @return the camera intent
     */
    fun getCameraIntent(activity: AppCompatActivity, requestCode: Int): Intent?

    /**
     * Gets the gallery intent
     *
     * @return the gallery intent
     */
    fun getGalleryIntent(): Intent

    /**
     * Gets an image URI from an intent received
     * from the camera
     *
     * @param intent the intent
     * @param context the Android context
     *
     * @return the image URI
     */
    fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri?

    /**
     * Gets an image URI from an intent received
     * from the gallery
     *
     * @param intent the intent
     *
     * @return the image URI
     */
    fun getImageUriFromGalleryIntent(intent: Intent?): Uri?

}