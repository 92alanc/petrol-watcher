package com.braincorp.petrolwatcher.base

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.v7.app.AppCompatActivity
import br.com.concretesolutions.kappuccino.utils.doWait
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.database.MockDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.MockAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.MockImageHandler
import com.braincorp.petrolwatcher.feature.stations.map.MockMapController
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.InputStream

open class BaseActivityTest<T: AppCompatActivity>(activityClass: Class<T>,
                                                  private val autoLaunch: Boolean = true) {

    val mockVehicleApi = MockWebServer()

    @Rule
    @JvmField
    val rule = if (autoLaunch) {
        IntentsTestRule(activityClass, true, false)
    } else {
        ActivityTestRule(activityClass, true, false)
    }

    @Before
    open fun setup() {
        DependencyInjection.init(DependencyInjection.Config(MockAuthenticator,
                MockImageHandler,
                MockDatabaseManager,
                MockMapController,
                mockVehicleApi.url("/vehicles/").toString()))

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

    fun launch() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        rule.launchActivity(intent())
        doWait(300)
    }

    fun getJsonFromAsset(fileName: String): String {
        val json: String

        try {
            val  inputStream: InputStream = rule.activity.assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }

        return json.replace("\n", "")
    }

}