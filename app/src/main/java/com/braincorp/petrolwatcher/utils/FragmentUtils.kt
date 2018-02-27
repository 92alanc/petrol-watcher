package com.braincorp.petrolwatcher.utils

import android.app.Fragment
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    fragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
}

fun AppCompatActivity.replaceFragmentPlaceholder(@IdRes placeholder: Int,
                                                 fragment: Fragment,
                                                 tag: String? = null) {
    fragmentManager.beginTransaction()
            .replace(placeholder, fragment, tag)
            .commit()
}