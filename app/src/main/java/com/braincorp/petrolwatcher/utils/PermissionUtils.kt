package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.annotation.TargetApi
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION_CODES.M
import android.support.v7.app.AppCompatActivity

@TargetApi(M)
fun AppCompatActivity.hasCameraPermission(): Boolean {
    return checkSelfPermission(CAMERA) == PERMISSION_GRANTED
}

@TargetApi(M)
fun AppCompatActivity.hasLocationPermission(): Boolean {
    val coarseLocation = checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
    val fineLocation = checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    return coarseLocation && fineLocation
}

@TargetApi(M)
fun AppCompatActivity.hasStoragePermission(): Boolean {
    val read = checkSelfPermission(READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    val write = checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    return read && write
}