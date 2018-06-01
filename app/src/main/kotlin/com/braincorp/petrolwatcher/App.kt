package com.braincorp.petrolwatcher

import android.app.Application
import com.google.firebase.FirebaseApp

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpFirebase()
    }

    private fun setUpFirebase() {
        FirebaseApp.initializeApp(this)
    }

}