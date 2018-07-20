package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.utils.rotateBitmap
import com.braincorp.petrolwatcher.feature.auth.utils.toUri
import com.braincorp.petrolwatcher.utils.hasCameraPermission

class AppImageHandler : ImageHandler {

    /**
     * Gets the camera intent
     *
     * @return the camera intent
     */
    override fun getCameraIntent(activity: AppCompatActivity, requestCode: Int): Intent? {
        return if (SDK_INT >= M) {
            if (activity.hasCameraPermission()) {
                getIntentForCamera()
            } else {
                activity.requestPermissions(arrayOf(CAMERA, READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE), requestCode)
                null
            }
        } else {
            getIntentForCamera()
        }
    }

    /**
     * Gets the gallery intent
     *
     * @return the gallery intent
     */
    override fun getGalleryIntent(): Intent {
        return Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    /**
     * Gets an image URI from an intent received
     * from the camera
     *
     * @param intent the intent
     * @param context the Android context
     *
     * @return the image URI
     */
    override fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri? {
        var bitmap = intent?.extras?.get("data") as Bitmap
        bitmap = rotateBitmap(bitmap, 270f)
        return bitmap.toUri(context)
    }

    /**
     * Gets an image URI from an intent received
     * from the gallery
     *
     * @param intent the intent
     *
     * @return the image URI
     */
    override fun getImageUriFromGalleryIntent(intent: Intent?): Uri? = intent?.data

    private fun getIntentForCamera(): Intent {
        return Intent(ACTION_IMAGE_CAPTURE)
    }

}