package com.braincorp.petrolwatcher

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import com.braincorp.petrolwatcher.database.AppDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.AppAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.AppImageHandler
import com.braincorp.petrolwatcher.feature.prediction.AveragePriceJobService
import com.braincorp.petrolwatcher.feature.prediction.DateReceiver
import com.braincorp.petrolwatcher.map.AppMapController
import com.braincorp.petrolwatcher.utils.getTimeUntilSaturday
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
        if (SDK_INT >= O) {
            val service = ComponentName(this, AveragePriceJobService::class.java)
            val jobId = 123
            val job = JobInfo.Builder(jobId, service)
                    .setOverrideDeadline(getTimeUntilSaturday()) // TODO: trigger every Saturday
                    .setBackoffCriteria(0, JobInfo.BACKOFF_POLICY_LINEAR)
                    .build()

            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(job)
        } else {
            registerReceiver(DateReceiver(), IntentFilter(Intent.ACTION_DATE_CHANGED))
        }
    }

}