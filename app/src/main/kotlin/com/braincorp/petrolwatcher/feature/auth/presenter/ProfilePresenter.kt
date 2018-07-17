package com.braincorp.petrolwatcher.feature.auth.presenter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import com.braincorp.petrolwatcher.feature.auth.utils.setProfilePictureAndDisplayName
import com.braincorp.petrolwatcher.feature.auth.utils.toUri
import com.braincorp.petrolwatcher.feature.auth.utils.uploadBitmap
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
     *                 gallery will be opened from
     * @param requestCode the request code
     */
    override fun openGallery(activity: AppCompatActivity, requestCode: Int) {
        val intent = imageHandler.getGalleryIntent()
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * Saves the profile
     *
     * @param picture the profile picture
     * @param displayName the display name
     * @param context the Android context
     */
    override fun saveProfile(picture: Bitmap?, displayName: String, context: Context) {
        uploadBitmap(picture, onSuccessAction = {
            setProfilePictureAndDisplayName(picture?.toUri(context), displayName,
                    onSuccessListener = this, onFailureListener = this)
        }, onFailureAction = {
            view.showErrorDialogue(R.string.error_profile_picture_display_name)
        })
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
        if (e.message != null)
            view.showErrorDialogue(e.message!!)
        else
            view.showErrorDialogue(R.string.error_profile_picture_display_name)
    }

}