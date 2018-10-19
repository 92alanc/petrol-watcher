package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M

/**
 * Determines whether the user has granted
 * the permission for the app to use the
 * camera
 *
 * @return true if positive, otherwise false
 */
@TargetApi(M)
fun Context.hasCameraPermission(): Boolean {
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
fun Context.hasExternalStoragePermission(): Boolean {
    val read = checkSelfPermission(READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    val write = checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    return read && write
}

@TargetApi(M)
fun Context.hasLocationPermission(): Boolean {
    val coarse = checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    val fine = checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    return coarse && fine
}
