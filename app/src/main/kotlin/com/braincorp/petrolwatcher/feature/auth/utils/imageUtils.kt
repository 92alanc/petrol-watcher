package com.braincorp.petrolwatcher.feature.auth.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

/**
 * Converts a bitmap to URI
 *
 * @param context the Android context
 *
 * @return the bitmap as URI
 */
fun Bitmap.toUri(context: Context): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val name = "img_${System.currentTimeMillis()}"
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, this,
            name, null)
    return Uri.parse(path)
}

/**
 * Converts a bitmap to a byte array
 *
 * @return the bitmap as a byte array
 */
fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    val quality = 50
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

/**
 * Rotates a bitmap
 *
 * @param bitmap the bitmap to rotate
 * @param angle the angle, in degrees
 *
 * @return the rotated bitmap
 */
fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
