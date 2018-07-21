package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.CAMERA
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
