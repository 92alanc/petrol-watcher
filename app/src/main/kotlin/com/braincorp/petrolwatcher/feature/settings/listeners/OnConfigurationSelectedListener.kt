package com.braincorp.petrolwatcher.feature.settings.listeners

import com.braincorp.petrolwatcher.feature.settings.model.Configuration

interface OnConfigurationSelectedListener {

    fun onConfigurationSelected(configuration: Configuration)

}