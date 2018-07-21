package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask

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

    /**
     * Uploads an image to cloud storage
     *
     * @param image the image
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    fun uploadImage(image: Bitmap?,
                    onSuccessListener: OnSuccessListener<UploadTask.TaskSnapshot>,
                    onFailureListener: OnFailureListener)

    /**
     * Sets a profile picture and a display name to
     * the current account
     *
     * @param picture the profile picture
     * @param displayName the display name
     * @param context the Android context
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    fun setProfilePictureAndDisplayName(picture: Bitmap?, displayName: String,
                                        context: Context,
                                        onSuccessListener: OnSuccessListener<Void>,
                                        onFailureListener: OnFailureListener)

}