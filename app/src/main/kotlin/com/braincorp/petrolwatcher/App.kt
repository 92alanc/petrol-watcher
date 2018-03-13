package com.braincorp.petrolwatcher

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupFirebase()
        setupDatabase()
        setupImageLoader()
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun setupDatabase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    private fun setupImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}