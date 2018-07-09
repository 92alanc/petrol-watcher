package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.CAMERA
import android.annotation.TargetApi
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M
import android.support.v7.app.AppCompatActivity

@TargetApi(M)
fun AppCompatActivity.hasCameraPermission(): Boolean {
    return checkSelfPermission(CAMERA) == PERMISSION_GRANTED
}
