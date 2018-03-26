package com.braincorp.petrolwatcher.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import com.braincorp.petrolwatcher.feature.petrolstations.listeners.OnStationsFoundListener
import com.braincorp.petrolwatcher.feature.petrolstations.model.Fuel

class StationLocatorService : IntentService(SERVICE_NAME) {

    companion object {
        private const val DEFAULT_RADIUS = 200f // = 200m
        const val MAX_RADIUS = 10000f // = 10km

        private const val TICK_INTERVAL = 1000L
        private const val TOTAL_COUNTDOWN_TIME = 60000L

        private const val EXTRA_FUEL = "fuel"
        private const val EXTRA_RADIUS = "radius"

        private const val SERVICE_NAME = "StationLocatorService"

        fun getIntent(context: Context,
                      fuel: Fuel? = null,
                      radius: Float = DEFAULT_RADIUS) : Intent {
            val intent = Intent(context, StationLocatorService::class.java)
            if (fuel != null) intent.putExtra(EXTRA_FUEL, fuel)
            intent.putExtra(EXTRA_RADIUS, radius)
            return intent
        }
    }

    private var isRunning = false

    var onStationsFoundListener: OnStationsFoundListener? = null

    override fun onBind(intent: Intent?): IBinder {
        isRunning = true
        return super.onBind(intent)
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onHandleIntent(intent: Intent?) {
        val fuel = intent?.getParcelableExtra(EXTRA_FUEL) as Fuel

        val radius = if (intent.getFloatExtra(EXTRA_RADIUS, DEFAULT_RADIUS) > MAX_RADIUS) MAX_RADIUS
        else intent.getFloatExtra(EXTRA_RADIUS, DEFAULT_RADIUS)

        locateStations(fuel, radius, onStationsFoundListener)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun locateStations(fuel: Fuel? = null,
                               radius: Float,
                               onStationsFoundListener: OnStationsFoundListener?) {
        object: CountDownTimer(TOTAL_COUNTDOWN_TIME, TICK_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (isRunning) start()
            }
        }.start()
    }

}