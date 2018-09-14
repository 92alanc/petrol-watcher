package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import com.braincorp.petrolwatcher.base.TestActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask
import org.mockito.Mock
import org.mockito.Mockito.mock

object MockImageHandler : ImageHandler {

    var uploadSuccess = true

    @Mock
    private val uri = mock(Uri::class.java)

    @Mock
    private val snapshot = mock(UploadTask.TaskSnapshot::class.java)

    /**
     * Opens the camera
     *
     * @param activity the activity where the camera will
     *                 be opened from
     * @param requestCode the request code
     *
     * @return the camera intent
     */
    override fun getCameraIntent(activity: AppCompatActivity, requestCode: Int): Intent? {
        return Intent(activity, TestActivity::class.java)
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
        return Intent(InstrumentationRegistry.getTargetContext(), TestActivity::class.java)
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
    override fun getImageUriFromCameraIntent(intent: Intent?, context: Context): Uri? = uri

    /**
     * Gets an image URI from an intent received
     * from the gallery
     *
     * @param intent the intent
     *
     * @return the image URI
     */
    override fun getImageUriFromGalleryIntent(intent: Intent?): Uri? = uri

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
        if (uploadSuccess)
            onSuccessListener.onSuccess(snapshot)
        else
            onFailureListener.onFailure(Exception())
    }

    /**
     * Sets a profile picture and a display name to
     * the current account
     *
     * @param picture the profile picture
     * @param displayName the display name
     * @param context the Android context
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun setProfilePictureAndDisplayName(picture: Bitmap?, displayName: String,
                                                 context: Context,
                                                 onSuccessListener: OnSuccessListener<Void>,
                                                 onFailureListener: OnFailureListener) {
        if (displayName == "Alan Camargo")
            onSuccessListener.onSuccess(null)
        else
            onFailureListener.onFailure(Exception())
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
        imageView.setImageResource(placeholderRes)
    }

}