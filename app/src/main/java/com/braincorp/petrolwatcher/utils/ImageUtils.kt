package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun Context.fillImageView(imageView: ImageView, uri: Uri?,
                          @DrawableRes placeholder: Int = -1,
                          rotation: Float = 0f) {
    Picasso.with(this)
            .load(uri)
            .placeholder(placeholder)
            .rotate(rotation)
            .fit()
            .into(imageView)
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

private fun Activity.openCameraAfterPermissionsGranted() {
    val intent = Intent(ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, REQUEST_CODE_CAMERA)
}