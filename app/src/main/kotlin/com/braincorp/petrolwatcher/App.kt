package com.braincorp.petrolwatcher

import android.app.Application
import com.braincorp.petrolwatcher.database.AppDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.AppAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.AppImageHandler
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * The general application class
 */
open class App : Application() {

    private companion object {
        const val VEHICLE_API_BASE_URL = "https://www.carqueryapi.com/api/0.3/"
    }

    override fun onCreate() {
        super.onCreate()
        setupFirebase()
        setupImageLoader()
        DependencyInjection.init(DependencyInjection.Config(AppAuthenticator(),
                AppImageHandler(),
                AppDatabaseManager(),
                VEHICLE_API_BASE_URL))
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun setupImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

}