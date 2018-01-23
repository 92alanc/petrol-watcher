package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.v7.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.io.File

fun AppCompatActivity.bitmapToUri(bitmap: Bitmap): Uri { // TODO: check if it's working
    val bytes = ByteArrayOutputStream()
    val quality = 100
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bytes)

    val time = System.currentTimeMillis()
    val name = "Image_$time"
    val description: String? = null
    val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, name, description)

    return Uri.fromFile(File(path))
}

fun AppCompatActivity.openCamera() {
    if (SDK_INT >= M) {
        if (hasCameraPermission() && hasStoragePermission())
            openCameraAfterPermissionsGranted()
        else
            requestPermissions(arrayOf(CAMERA,
                    READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE), REQUEST_CODE_CAMERA)
    } else {
        openCameraAfterPermissionsGranted()
    }
}

fun AppCompatActivity.openGallery() {
    val intent = Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, REQUEST_CODE_GALLERY)
}

private fun AppCompatActivity.openCameraAfterPermissionsGranted() {
    val intent = Intent(ACTION_IMAGE_CAPTURE)
    startActivityForResult(intent, REQUEST_CODE_CAMERA)
}