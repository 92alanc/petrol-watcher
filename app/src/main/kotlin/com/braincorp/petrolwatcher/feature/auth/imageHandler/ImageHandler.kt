package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Intent
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

}