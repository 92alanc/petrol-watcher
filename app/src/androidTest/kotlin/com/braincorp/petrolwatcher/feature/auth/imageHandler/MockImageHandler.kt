package com.braincorp.petrolwatcher.feature.auth.imageHandler

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.TestActivity

object MockImageHandler : ImageHandler {

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

}