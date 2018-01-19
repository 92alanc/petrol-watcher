package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        // TODO: setContentView
    }

    fun showProgressBar() {
        // TODO: implement
    }

    fun hideProgressBar() {
        // TODO: implement
    }

}