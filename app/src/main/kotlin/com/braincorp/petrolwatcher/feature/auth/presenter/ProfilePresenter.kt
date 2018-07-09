package com.braincorp.petrolwatcher.feature.auth.presenter

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.utils.hasCameraPermission

/**
 * The implementation of the presentation layer
 * of the profile activity
 */
class ProfilePresenter : ProfileContract.Presenter {

    /**
     * Opens the camera
     *
     * @param activity the activity where the
     * camera will be opened from
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
     * gallery will be opened from
     * @param requestCode the request code
     */
    override fun openGallery(activity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, requestCode)
    }

    private fun openCameraAfterPermissionGranted(activity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, requestCode)
    }

}