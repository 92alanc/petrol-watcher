package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import com.braincorp.petrolwatcher.R
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener
import java.io.ByteArrayOutputStream

fun Context.fillImageView(uri: Uri?, imageView: ImageView,
                  @DrawableRes placeholder: Int = 0,
                  progressBar: ProgressBar) {
    val imageLoader = ImageLoader.getInstance()
    val options = DisplayImageOptions.Builder()
            .showImageOnLoading(placeholder)
            .showImageForEmptyUri(placeholder)
            .showImageOnFail(placeholder)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build()

    val imageLoadingListener = object: ImageLoadingListener {
        override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
            progressBar.visibility = GONE
        }

        override fun onLoadingStarted(imageUri: String?, view: View?) {
            progressBar.visibility = VISIBLE
            progressBar.progress = 0
        }

        override fun onLoadingCancelled(imageUri: String?, view: View?) {
            progressBar.progress = 0
            progressBar.visibility = GONE
        }

        override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
            progressBar.progress = 0
            progressBar.visibility = GONE

            showErrorDialogue(R.string.error_loading_image)
        }

    }

    val progressListener = ImageLoadingProgressListener { _, _, current, total ->
        progressBar.progress = ((current * 100) / total)
    }

    imageLoader.displayImage(uri?.toString(), imageView, options,
            imageLoadingListener, progressListener)
}

fun Activity.openCamera() {
    if (SDK_INT >= M) {
        if ((this as AppCompatActivity).hasCameraPermission() && hasStoragePermission())
            openCameraAfterPermissionsGranted()
        else
            requestPermissions(arrayOf(CAMERA,
                    READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE), REQUEST_CODE_CAMERA)
    } else {
        openCameraAfterPermissionsGranted()
    }
}

fun Activity.openGallery() {
    val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, REQUEST_CODE_GALLERY)
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    val quality = 50
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

private fun Activity.openCameraAfterPermissionsGranted() {
    val intent = Intent(ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, REQUEST_CODE_CAMERA)
}