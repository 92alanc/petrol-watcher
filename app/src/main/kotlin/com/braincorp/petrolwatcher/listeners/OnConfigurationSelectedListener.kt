package com.braincorp.petrolwatcher.listeners

import com.braincorp.petrolwatcher.preferences.Configuration

interface OnConfigurationSelectedListener {
    fun onConfigurationSelected(configuration: Configuration)
}