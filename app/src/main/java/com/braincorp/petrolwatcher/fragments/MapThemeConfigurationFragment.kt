package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import com.braincorp.petrolwatcher.preferences.Configuration
import com.braincorp.petrolwatcher.preferences.MapTheme

class MapThemeConfigurationFragment : ConfigurationFragment() {

    companion object {
        fun newInstance(mapTheme: MapTheme): MapThemeConfigurationFragment {
            val instance = MapThemeConfigurationFragment()

            val args = Bundle()
            args.putSerializable(ARG_CONFIGURATION, mapTheme)
            instance.arguments = args

            return instance
        }
    }

    override fun onConfigurationSelected(configuration: Configuration) {
        preferenceManager.setMapTheme(configuration as MapTheme)
        textViewDescription.text = configuration.getDescription(activity)
    }

}