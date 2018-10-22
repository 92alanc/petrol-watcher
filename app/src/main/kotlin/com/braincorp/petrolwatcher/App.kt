package com.braincorp.petrolwatcher

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.graphics.Typeface
import com.braincorp.petrolwatcher.database.AppDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.AppAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.AppImageHandler
import com.braincorp.petrolwatcher.feature.prediction.AveragePriceService
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
        registerDateReceiver()
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

    private fun registerDateReceiver() {
        val service = ComponentName(this, AveragePriceService::class.java)
        val jobId = 123
        val job = JobInfo.Builder(jobId, service)
                .setPeriodic(getWeekInMillis())
                .setBackoffCriteria(0, JobInfo.BACKOFF_POLICY_LINEAR)
                .build()

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(job)
    }

}