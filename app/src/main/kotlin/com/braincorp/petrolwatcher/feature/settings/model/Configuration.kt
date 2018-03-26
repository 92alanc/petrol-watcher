package com.braincorp.petrolwatcher.feature.settings.model

import android.content.Context
import java.io.Serializable

interface Configuration : Serializable {

    fun findByIndex(index: Int): Configuration

    fun getDescription(context: Context): String

    fun getName(context: Context): String

}