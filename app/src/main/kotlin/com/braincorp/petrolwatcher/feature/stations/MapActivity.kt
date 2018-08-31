package com.braincorp.petrolwatcher.feature.stations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R

/**
 * The activity where the map containing all petrol stations
 * nearby is shown
 */
class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }

}