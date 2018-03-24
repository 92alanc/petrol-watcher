package com.braincorp.petrolwatcher.feature.settings.fragments

import android.os.Bundle
import com.braincorp.petrolwatcher.feature.settings.model.Configuration
import com.braincorp.petrolwatcher.feature.settings.model.SystemOfMeasurement

class SystemOfMeasurementConfigurationFragment : ConfigurationFragment() {

    companion object {
        fun newInstance(systemOfMeasurement: SystemOfMeasurement)
                : SystemOfMeasurementConfigurationFragment {
            val instance = SystemOfMeasurementConfigurationFragment()

            val args = Bundle()
            args.putSerializable(ARG_CONFIGURATION, systemOfMeasurement)
            instance.arguments = args

            return instance
        }
    }

    override fun onConfigurationSelected(configuration: Configuration) {
        preferenceManager.setSystemOfMeasurement(configuration as SystemOfMeasurement)
        textViewDescription.text = configuration.getDescription(activity)
    }

}