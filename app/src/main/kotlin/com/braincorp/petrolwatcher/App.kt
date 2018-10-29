package com.braincorp.petrolwatcher

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.graphics.Typeface
import com.braincorp.petrolwatcher.database.AppDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.AppAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.AppImageHandler
import com.braincorp.petrolwatcher.feature.prediction.service.AveragePriceService
import com.braincorp.petrolwatcher.map.AppMapController
import com.braincorp.petrolwatcher.utils.getWeekInMillis
import com.google.firebase.FirebaseApp
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * The general application class
 */
@Suppress("unused")
open class App : Application() {

    private companion object {
        const val VEHICLE_API_BASE_URL = "https://www.carqueryapi.com/api/0.3/"
    }

    override fun onCreate() {
        super.onCreate()
        overrideFont()
        setupFirebase()
        setupImageLoader()
        DependencyInjection.init(DependencyInjection.Config(AppAuthenticator(),
                                                            AppImageHandler(),
                                                            AppDatabaseManager(),
                                                            AppMapController(),
                                                            VEHICLE_API_BASE_URL))
        scheduleAveragePriceService()
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun setupImageLoader() {
        val config = ImageLoaderConfiguration.createDefault(this)
        ImageLoader.getInstance().init(config)
    }

    private fun overrideFont() {
        val typeface = Typeface.createFromAsset(assets, "fonts/Nunito-Regular.ttf")
        val field = Typeface::class.java.getDeclaredField("SERIF")
        field.isAccessible = true
        field.set(null, typeface)
    }

    private fun scheduleAveragePriceService() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = 123
        val flags = 0
        val intent = AveragePriceService.getIntent(this)
        val task = PendingIntent.getService(this, requestCode, intent, flags)
        alarmManager.setInexactRepeating(AlarmManager.RTC,
                                         System.currentTimeMillis() + 5000, // TODO: use getTimeLeftUntilSaturday()
                                         getWeekInMillis(),
                                         task)
    }

}