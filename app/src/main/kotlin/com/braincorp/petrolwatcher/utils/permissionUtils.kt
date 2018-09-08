package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.annotation.TargetApi
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M
import android.support.v7.app.AppCompatActivity

/**
 * Determines whether the user has granted
 * the permission for the app to use the
 * camera
 *
 * @return true if positive, otherwise false
 */
@TargetApi(M)
fun AppCompatActivity.hasCameraPermission(): Boolean {
    return checkSelfPermission(CAMERA) == PERMISSION_GRANTED
}

/**
 * Determines whether the user has granted
 * the permission for the app to read and
 * write on the external storage
 *
 * @return true if positive, otherwise false
 */
@TargetApi(M)
fun AppCompatActivity.hasExternalStoragePermission(): Boolean {
    val read = checkSelfPermission(READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    val write = checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    return read && write
}

@TargetApi(M)
fun AppCompatActivity.hasLocationPermission(): Boolean {
    val coarse = checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    val fine = checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    return coarse && fine
}
