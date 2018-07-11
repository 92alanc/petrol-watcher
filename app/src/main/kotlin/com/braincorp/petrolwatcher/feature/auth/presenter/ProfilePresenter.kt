package com.braincorp.petrolwatcher.feature.auth.presenter

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.utils.setProfilePictureAndDisplayName
import com.braincorp.petrolwatcher.feature.auth.utils.uploadBitmap
import com.braincorp.petrolwatcher.utils.hasCameraPermission
import com.braincorp.petrolwatcher.feature.auth.utils.toUri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.lang.Exception

/**
 * The implementation of the presentation layer
 * of the profile activity
 */
class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter,
        OnSuccessListener<Void>, OnFailureListener {

    /**
     * Opens the camera
     *
     * @param activity the activity where the
     *                 camera will be opened from
     * @param requestCode the request code
     */
    override fun openCamera(activity: AppCompatActivity, requestCode: Int) {
        if (SDK_INT >= M) {
            if (activity.hasCameraPermission()) {
                openCameraAfterPermissionGranted(activity, requestCode)
            } else {
                activity.requestPermissions(arrayOf(CAMERA, READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE), requestCode)
            }
        } else {
            openCameraAfterPermissionGranted(activity, requestCode)
        }
    }

    /**
     * Opens the gallery
     *
     * @param activity the activity where the
     *                 gallery will be opened from
     * @param requestCode the request code
     */
    override fun openGallery(activity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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

    private fun openCameraAfterPermissionGranted(activity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, requestCode)
    }

}