package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.TestActivity
import org.mockito.Mock
import org.mockito.Mockito.mock

object MockImageHandler : ImageHandler {

    @Mock
    private val uri = mock(Uri::class.java)

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
     * @return the gallery intent
     */
    override fun getGalleryIntent(): Intent {
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

}