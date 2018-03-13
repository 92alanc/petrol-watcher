package com.braincorp.petrolwatcher.preferences

import android.content.Context
import java.io.Serializable

interface Configuration : Serializable {

    fun findByIndex(index: Int): Configuration

    fun getDescription(context: Context): String

    fun getName(context: Context): String

}