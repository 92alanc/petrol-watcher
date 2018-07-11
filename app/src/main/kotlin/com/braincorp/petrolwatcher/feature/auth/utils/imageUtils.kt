package com.braincorp.petrolwatcher.feature.auth.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.support.annotation.DrawableRes
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener
import java.io.ByteArrayOutputStream

fun fillImageView(imageUri: Uri?,
                  imageView: ImageView,
                  @DrawableRes placeholderRes: Int = 0,
                  progressBar: ProgressBar) {
    val imageLoader = ImageLoader.getInstance()
    val options = DisplayImageOptions.Builder()
            .showImageOnLoading(placeholderRes)
            .showImageForEmptyUri(placeholderRes)
            .showImageOnFail(placeholderRes)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
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

            Log.e("A", "Error loading image", failReason?.cause)
        }

    }

    val progressListener = ImageLoadingProgressListener { _, _, current, total ->
        progressBar.progress = ((current * 100) / total)
    }

    imageLoader.displayImage(imageUri?.toString(), imageView, options,
            imageLoadingListener, progressListener)
}

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
 * Rotates a bitmap
 *
 * @param bitmap the bitmap
 * @param angle the angle, in degrees
 *
 * @return the rotated bitmap
 */
fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    val quality = 50
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}
