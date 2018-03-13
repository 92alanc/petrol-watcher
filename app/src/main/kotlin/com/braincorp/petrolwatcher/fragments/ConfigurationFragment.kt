package com.braincorp.petrolwatcher.fragments

import android.app.Dialog
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.preferences.Configuration
import com.braincorp.petrolwatcher.preferences.PreferenceManager
import com.braincorp.petrolwatcher.view.RadioGroupDialogue

abstract class ConfigurationFragment : Fragment(), View.OnClickListener,
        RadioGroupDialogue.OnConfigurationSelectedListener {

    companion object {
        // This argument should be passed in all implementation instances
        const val ARG_CONFIGURATION = "configuration"
    }

    var dialogue: Dialog? = null

    lateinit var preferenceManager: PreferenceManager
    lateinit var textViewDescription: TextView

    private lateinit var configuration: Configuration
    private lateinit var rootView: ViewGroup
    private lateinit var textViewTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)
        preferenceManager = PreferenceManager(activity)
        parseArgs()
        bindViews(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        rootView.setOnClickListener(this)
        if (dialogue is RadioGroupDialogue) {
            (dialogue as RadioGroupDialogue).onConfigurationSelectedListener = this
        }
    }

    abstract override fun onConfigurationSelected(configuration: Configuration)

    override fun onClick(v: View?) {
        dialogue?.show()
    }

    private fun bindViews(view: View) {
        rootView = view.findViewById(R.id.rootView)

        textViewTitle = view.findViewById(R.id.textViewTitle)
        textViewTitle.text = configuration.getName(activity)

        textViewDescription = view.findViewById(R.id.textViewDescription)
        textViewDescription.text = configuration.getDescription(activity)
    }

    private fun parseArgs() {
        configuration = arguments.getSerializable(ARG_CONFIGURATION) as Configuration
    }

}