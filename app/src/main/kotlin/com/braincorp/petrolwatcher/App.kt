package com.braincorp.petrolwatcher

import android.app.Application
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * The general application class
 */
@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupFirebase()
        setupImageLoader()
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun setupImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}