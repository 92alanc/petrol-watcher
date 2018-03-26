package com.braincorp.petrolwatcher.feature.users.fragments

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.google.firebase.auth.FirebaseAuth

class DisplayNameFragment : Fragment(), AdaptableUi {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: AdaptableUi.Mode): DisplayNameFragment {
            val instance = DisplayNameFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewDisplayName: TextView
    private lateinit var editTextDisplayName: EditText

    private var uiMode = AdaptableUi.Mode.INITIAL

    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display_name, container, false)
        parseArgs()
        bindViews(view)
        prepareUi()
        return view
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        textViewDisplayName.visibility = GONE
        editTextDisplayName.visibility = VISIBLE
    }

    override fun prepareEditMode() {
        textViewDisplayName.visibility = GONE

        editTextDisplayName.visibility = VISIBLE
        editTextDisplayName.setText(user?.displayName)
    }

    override fun prepareViewMode() {
        textViewDisplayName.visibility = VISIBLE
        textViewDisplayName.text = user?.displayName

        editTextDisplayName.visibility = GONE
    }

    fun getDisplayName(): String {
        return if (uiMode == AdaptableUi.Mode.CREATE || uiMode == AdaptableUi.Mode.EDIT)
            editTextDisplayName.text.toString()
        else user?.displayName!!
    }

    private fun bindViews(view: View) {
        textViewDisplayName = view.findViewById(R.id.textViewDisplayName)
        editTextDisplayName = view.findViewById(R.id.editTextDisplayName)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE) as AdaptableUi.Mode
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

}