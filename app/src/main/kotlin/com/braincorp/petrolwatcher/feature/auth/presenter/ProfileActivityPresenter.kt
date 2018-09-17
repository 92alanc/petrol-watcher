package com.braincorp.petrolwatcher.feature.auth.presenter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

/**
 * The implementation of the presentation layer
 * of the profile activity
 *
 * @param view the view layer
 * @param imageHandler the image handler
 */
class ProfileActivityPresenter(private val view: ProfileContract.View,
                               private val imageHandler: ImageHandler) : ProfileContract.Presenter,
        OnSuccessListener<Void>, OnFailureListener {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

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
        if (displayName.isNotBlank()) {
            if (drawable != null) {
                val profilePicture = (drawable as BitmapDrawable).bitmap
                imageHandler.uploadImage(profilePicture, OnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl.addOnSuccessListener {
                        setProfilePictureAndDisplayName(it, displayName, context)
                    }
                }, OnFailureListener {
                    view.showErrorDialogue(R.string.error_profile_picture_display_name)
                })
            } else {
                setProfilePictureAndDisplayName(null, displayName, context)
            }
        } else {
            view.showBlankNameError()
        }
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
        Log.e(TAG, "Error updating profile picture and display name", e)
        view.showErrorDialogue(R.string.error_profile_picture_display_name)
    }

    private fun setProfilePictureAndDisplayName(profilePictureUri: Uri?,
                                                displayName: String,
                                                context: Context) {
        imageHandler.setProfilePictureAndDisplayName(profilePictureUri,
                displayName, context,
                onSuccessListener = this, onFailureListener = this)
    }

}