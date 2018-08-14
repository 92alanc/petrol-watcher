package com.braincorp.petrolwatcher.base

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity
import br.com.concretesolutions.kappuccino.utils.doWait
import com.braincorp.petrolwatcher.App
import com.braincorp.petrolwatcher.TestApp
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.InputStream

open class BaseActivityTest<T: AppCompatActivity>(activityClass: Class<T>,
                                                  private val autoLaunch: Boolean = true) {

    val mockVehicleApi = MockWebServer()

    private lateinit var app: App

    @Rule
    @JvmField
    val rule = if (autoLaunch) {
        IntentsTestRule(activityClass, true, false)
    } else {
        ActivityTestRule(activityClass, true, false)
    }

    @Before
    open fun setup() {
        app = InstrumentationRegistry.getTargetContext().applicationContext as TestApp
        mockVehicleApi.start()
        mockVehicleApi.url(getVehiclesApiBaseUrl())

        if (autoLaunch) launch()
        else Intents.init()
    }

    @After
    open fun afterTest() {
        if (!autoLaunch) Intents.release()
    }

    open fun intent(): Intent {
        return Intent()
    }

    fun getAuthenticator(): Authenticator {
        return app.dependencyInjection().getAuthenticator()
    }

    fun getImageHandler(): ImageHandler {
        return app.dependencyInjection().getImageHandler()
    }

    fun launch() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        rule.launchActivity(intent())
        doWait(300)
    }

    fun getJsonFromAsset(path: String): String? {
        val json: String?
        try {
            val  inputStream: InputStream = rule.activity.assets.open(path)
            json = inputStream.bufferedReader().use{ it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun getVehiclesApiBaseUrl(): String {
        return app.dependencyInjection().getVehiclesApiBaseUrl()
    }

}