package com.braincorp.petrolwatcher.utils

import android.Manifest.permission.*
import android.annotation.TargetApi
import android.os.Build.VERSION_CODES.M
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

const val REQUEST_CODE_LOCATION = 3892
const val REQUEST_CODE_CAMERA = 3893
const val REQUEST_CODE_STORAGE = 3894

fun AppCompatActivity.replaceFragmentPlaceholder(@IdRes placeholder: Int,
                                                 fragment: Fragment,
                                                 tag: String? = null) {
    supportFragmentManager.beginTransaction()
            .replace(placeholder, fragment, tag)
            .commit()
}

@TargetApi(M)
fun AppCompatActivity.requestLocationPermission() {
    requestPermissionGroup(REQUEST_CODE_LOCATION,
            ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
}

@TargetApi(M)
fun AppCompatActivity.requestCameraPermission() {
    requestPermission(CAMERA, REQUEST_CODE_CAMERA)
}

@TargetApi(M)
fun AppCompatActivity.requestStoragePermission() {
    requestPermissionGroup(REQUEST_CODE_STORAGE, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
}

@TargetApi(M)
private fun AppCompatActivity.requestPermissionGroup(requestCode: Int,
                                                     vararg permissionGroup: String) {
    requestPermissions(permissionGroup, requestCode)
}

@TargetApi(M)
private fun AppCompatActivity.requestPermission(permission: String, requestCode: Int) {
    requestPermissions(arrayOf(permission), requestCode)
}