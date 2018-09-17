package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.Manifest.permission.*
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.braincorp.petrolwatcher.feature.auth.utils.rotateBitmap
import com.braincorp.petrolwatcher.feature.auth.utils.toByteArray
import com.braincorp.petrolwatcher.feature.auth.utils.toUri
import com.braincorp.petrolwatcher.utils.hasCameraPermission
import com.braincorp.petrolwatcher.utils.hasExternalStoragePermission
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener

class AppImageHandler : ImageHandler {

    private companion object {
        const val TAG = "PETROL_WATCHER"
    }

    private var imageLoaderBusy = false

    /**
     * Gets the camera intent
     *
     * @return the camera intent
     */
    override fun getCameraIntent(activity: AppCompatActivity, requestCode: Int): Intent? {
        return if (SDK_INT >= M) {
            if (activity.hasCameraPermission()) {
                getIntentForCamera()
            } else {
                activity.requestPermissions(arrayOf(CAMERA, READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE), requestCode)
                null
            }
        } else {
            getIntentForCamera()
        }
    }

    /**
     * Gets the gallery intent
     *
     * @param activity the activity where the gallery will
     *                 be opened from
     * @param requestCode the request code for the external
     *                    storage permission
     *
     * @return the gallery intent
     */
    override fun getGalleryIntent(activity: AppCompatActivity, requestCode: Int): Intent? {
        return if (SDK_INT >= M) {
            if (activity.hasExternalStoragePermission()) {
                getIntentForGallery()
            } else {
                activity.requestPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                        requestCode)
                null
            }
        } else {
            getIntentForCamera()
        }
    }

    /**
     * Gets an image URI from an intent received
     * from the camera
     *
     * @param intent the intent
     * @param context the Android context
     *
     * @return the image URI
     */
    override fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri? {
        var bitmap = intent?.extras?.get("data") as Bitmap
        bitmap = rotateBitmap(bitmap, 270f)
        return bitmap.toUri(context)
    }

    /**
     * Gets an image URI from an intent received
     * from the gallery
     *
     * @param intent the intent
     *
     * @return the image URI
     */
    override fun getImageUriFromGalleryIntent(intent: Intent?): Uri? = intent?.data

    /**
     * Uploads an image to cloud storage
     *
     * @param image the image
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun uploadImage(image: Bitmap?,
                             onSuccessListener: OnSuccessListener<UploadTask.TaskSnapshot>,
                             onFailureListener: OnFailureListener) {
        val rootReference = FirebaseStorage.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val name = "profile.jpg"
        val pictureReference = rootReference.child("users/$uid/$name")

        val data = image?.toByteArray() ?: return

        pictureReference.putBytes(data)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
    }

    /**
     * Sets a profile picture and a display name to
     * the current account
     *
     * @param pictureUri the profile picture URI
     * @param displayName the display name
     * @param context the Android context
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun setProfilePictureAndDisplayName(pictureUri: Uri?, displayName: String,
                                                 context: Context,
                                                 onSuccessListener: OnSuccessListener<Void>,
                                                 onFailureListener: OnFailureListener) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.updateProfile(UserProfileChangeRequest.Builder()
                .setPhotoUri(pictureUri)
                .setDisplayName(displayName)
                .build())
                ?.addOnSuccessListener(onSuccessListener)
                ?.addOnFailureListener(onFailureListener)
    }

    /**
     * Fills an ImageView
     *
     * @param imageUri the image URI
     * @param imageView the ImageView
     * @param placeholderRes the optional image to be placed in case
     *                       the image URI is null
     * @param progressBar the progress bar to show when the image
     *                    is loading
     */
    override fun fillImageView(imageUri: Uri?,
                               imageView: ImageView,
                               placeholderRes: Int,
                               progressBar: ProgressBar) {
        if (imageLoaderBusy) return

        if (imageUri == null) {
            imageView.setImageResource(placeholderRes)
            return
        }

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
                progressBar.visibility = View.GONE
                Log.d(TAG, "Finished loading image. Image URI: $imageUri")
                imageLoaderBusy = false
            }

            override fun onLoadingStarted(imageUri: String?, view: View?) {
                progressBar.visibility = View.VISIBLE
                progressBar.progress = 0
                Log.d(TAG, "Started loading image. Image URI: $imageUri")
                imageLoaderBusy = true
            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {
                progressBar.progress = 0
                progressBar.visibility = View.GONE
                Log.w(TAG, "Cancelled loading image. Image URI: $imageUri")
                imageLoaderBusy = false
            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                progressBar.progress = 0
                progressBar.visibility = View.GONE
                Log.e(TAG, "Error loading image. Image URI: $imageUri", failReason?.cause)
                imageLoaderBusy = false
            }

        }

        val progressListener = ImageLoadingProgressListener { _, _, current, total ->
            progressBar.progress = ((current * 100) / total)
        }

        imageLoader.displayImage(imageUri.toString(), imageView, options,
                                 imageLoadingListener, progressListener)
    }

    private fun getIntentForCamera(): Intent {
        return Intent(ACTION_IMAGE_CAPTURE)
    }

    private fun getIntentForGallery(): Intent {
        return Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

}