package com.braincorp.petrolwatcher.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Window.FEATURE_NO_TITLE
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.utils.startMainActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * The splash activity
 */
class SplashActivity : AppCompatActivity() {

    private companion object {
        const val TIMER_LIMIT = 2000L
        const val TIMER_INTERVAL = 20L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        activateTimer()
    }

    private fun activateTimer() {
        var i = 0
        progress_bar.progress = i
        object: CountDownTimer(TIMER_LIMIT, TIMER_INTERVAL) {
            override fun onFinish() {
                startMainActivity(finishCurrent = true)
            }

            override fun onTick(millisUntilFinished: Long) {
                i++
                val progress = ((i * 100) / (TIMER_LIMIT / TIMER_INTERVAL)).toInt()
                progress_bar.progress = progress
            }
        }.start()
    }

}