package com.braincorp.petrolwatcher.utils

import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.App
import com.braincorp.petrolwatcher.DependencyInjection

fun AppCompatActivity.dependencyInjection(): DependencyInjection {
    return (applicationContext as App).dependencyInjection()
}
