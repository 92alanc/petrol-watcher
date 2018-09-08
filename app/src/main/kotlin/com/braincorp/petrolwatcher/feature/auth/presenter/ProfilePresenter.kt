package com.braincorp.petrolwatcher.feature.auth.presenter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.lang.Exception

/**
 * The implementation of the presentation layer
 * of the profile activity
 *
 * @param view the view layer
 * @param imageHandler the image handler
 */
class ProfilePresenter(private val view: ProfileContract.View,
                       private val imageHandler: ImageHandler) : ProfileContract.Presenter,
        OnSuccessListener<Void>, OnFailureListener {

    /**
     * Opens the camera
     *
     * @param activity the activity where the
     *                 camera will be opened from
     * @param requestCode the request code
     */
    override fun openCamera(activity: AppCompatActivity, requestCode: Int) {
        val intent = imageHandler.getCameraIntent(activity, requestCode)
        if (intent != null)
            activity.startActivityForResult(intent, requestCode)
    }

    /**
     * Opens the gallery
     *
     * @param activity the activity where the
     * gallery will be opened from
     * @param requestCode the request code
     */
    override fun openGallery(activity: AppCompatActivity, requestCode: Int) {
        val intent = imageHandler.getGalleryIntent(activity, requestCode)
        if (intent != null)
            activity.startActivityForResult(intent, requestCode)
    }

    /**
     * Saves the profile
     *
     * @param drawable the drawable taken from an image view
     * @param displayName the display name
     * @param context the Android context
     */
    override fun saveProfile(drawable: Drawable?, displayName: String, context: Context) {
        val profilePicture = if (drawable != null && drawable !is VectorDrawable)
            (drawable as BitmapDrawable).bitmap
        else
            null
        imageHandler.uploadImage(profilePicture, OnSuccessListener {
            imageHandler.setProfilePictureAndDisplayName(profilePicture, displayName, context,
                    onSuccessListener = this, onFailureListener = this)
        }, OnFailureListener {
            view.showErrorDialogue(R.string.error_profile_picture_display_name)
        })
    }

    /**
     * Gets an image URI from a camera intent
     *
     * @param intent the intent
     * @param context the Android context
     *
     * @return the image URI
     */
    override fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri? {
        return imageHandler.getImageUriFromCameraIntent(intent, context)
    }

    /**
     * Gets an image URI from a gallery intent
     *
     * @param intent the intent
     *
     * @return the image URI
     */
    override fun getImageUriFromGalleryIntent(intent: Intent?): Uri? {
        return imageHandler.getImageUriFromGalleryIntent(intent)
    }

    /**
     * Action taken when both profile picture and display
     * name are successfully set
     *
     * @param task the task
     */
    override fun onSuccess(task: Void?) {
        view.showMap()
    }

    /**
     * Action taken when either profile picture and display
     * name can't be set
     *
     * @param e the exception
     */
    override fun onFailure(e: Exception) {
        view.showErrorDialogue(R.string.error_profile_picture_display_name)
    }

}