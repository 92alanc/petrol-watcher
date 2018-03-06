package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.fragments.MapThemeConfigurationFragment
import com.braincorp.petrolwatcher.fragments.SystemOfMeasurementConfigurationFragment
import com.braincorp.petrolwatcher.preferences.MapTheme
import com.braincorp.petrolwatcher.preferences.PreferenceManager
import com.braincorp.petrolwatcher.preferences.SystemOfMeasurement
import com.braincorp.petrolwatcher.utils.replaceFragmentPlaceholder
import com.braincorp.petrolwatcher.view.DialogueType
import com.braincorp.petrolwatcher.view.RadioGroupDialogue
import com.braincorp.petrolwatcher.view.RadioGroupDialogueFactory
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), DialogInterface.OnShowListener {

    companion object {
        private const val KEY_MEASUREMENT = "key_measurement"
        private const val KEY_MAP_THEME = "key_map_theme"

        private const val TAG_MEASUREMENT = "tag_measurement"
        private const val TAG_MAP_THEME = "tag_map_theme"

        fun getIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    private lateinit var measurementFragment: SystemOfMeasurementConfigurationFragment
    private lateinit var mapThemeFragment: MapThemeConfigurationFragment
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        preferenceManager = PreferenceManager(context = this)
        if (savedInstanceState != null) parseSavedInstanceState(savedInstanceState)
        else loadFragments()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(KEY_MEASUREMENT, measurementFragment.tag)
        outState?.putString(KEY_MAP_THEME, mapThemeFragment.tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
    }

    override fun onShow(dialogue: DialogInterface?) {
        (dialogue as? RadioGroupDialogue)?.inflate()
    }

    private fun loadFragments() {
        loadMeasurementFragment()
        loadMapThemeFragment()
    }

    private fun loadMeasurementFragment() {
        val systemOfMeasurement = preferenceManager.getSystemOfMeasurement()
        val fragment = SystemOfMeasurementConfigurationFragment.newInstance(systemOfMeasurement)

        val dialogue = RadioGroupDialogueFactory.makeDialogue(context = this,
                title = R.string.system_of_measurement,
                type = DialogueType.VERTICAL,
                data = SystemOfMeasurement.toMap())
        dialogue.setOnShowListener(this)
        fragment.dialogue = dialogue

        replaceFragmentPlaceholder(R.id.placeholderMeasurement,
                fragment,
                TAG_MEASUREMENT)
    }

    private fun loadMapThemeFragment() {
        val mapTheme = preferenceManager.getMapTheme()
        val fragment = MapThemeConfigurationFragment.newInstance(mapTheme)

        val dialogue = RadioGroupDialogueFactory.makeDialogue(context = this,
                title = R.string.map_theme,
                type = DialogueType.HORIZONTAL,
                data = MapTheme.toMap())
        dialogue.setOnShowListener(this)
        fragment.dialogue = dialogue

        replaceFragmentPlaceholder(R.id.placeholderMapTheme,
                fragment,
                TAG_MAP_THEME)
    }

    private fun parseSavedInstanceState(savedInstanceState: Bundle) {
        val measurementTag = savedInstanceState.getString(KEY_MEASUREMENT)
        val mapThemeTag = savedInstanceState.getString(KEY_MAP_THEME)

        measurementFragment = fragmentManager.findFragmentByTag(measurementTag)
                as SystemOfMeasurementConfigurationFragment
        mapThemeFragment = fragmentManager.findFragmentByTag(mapThemeTag)
                as MapThemeConfigurationFragment
    }

}