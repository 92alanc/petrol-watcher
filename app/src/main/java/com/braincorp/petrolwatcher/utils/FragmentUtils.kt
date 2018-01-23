package com.braincorp.petrolwatcher.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragmentPlaceholder(@IdRes placeholder: Int,
                                                 fragment: Fragment,
                                                 tag: String? = null) {
    supportFragmentManager.beginTransaction()
            .replace(placeholder, fragment, tag)
            .commit()
}