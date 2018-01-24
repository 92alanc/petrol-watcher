package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode
import com.google.firebase.auth.FirebaseAuth

class DisplayNameFragment : Fragment() {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: UiMode): DisplayNameFragment {
            val instance = DisplayNameFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewDisplayName: TextView
    private lateinit var editTextDisplayName: EditText

    private lateinit var uiMode: UiMode

    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_display_name, container, false)
        parseArgs()
        bindViews(view)
        prepareUi()
        return view
    }

    fun getDisplayName(): String {
        return if (uiMode == UiMode.CREATE || uiMode == UiMode.EDIT)
            editTextDisplayName.text.toString()
        else user?.displayName!!
    }

    private fun bindViews(view: View) {
        textViewDisplayName = view.findViewById(R.id.textViewDisplayName)
        editTextDisplayName = view.findViewById(R.id.editTextDisplayName)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE) as UiMode
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareNewAccountMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareNewAccountMode() {
        textViewDisplayName.visibility = GONE
        editTextDisplayName.visibility = VISIBLE
    }

    private fun prepareEditMode() {
        textViewDisplayName.visibility = GONE

        editTextDisplayName.visibility = VISIBLE
        editTextDisplayName.setText(user?.displayName)
    }

    private fun prepareViewMode() {
        textViewDisplayName.visibility = VISIBLE
        textViewDisplayName.text = user?.displayName

        editTextDisplayName.visibility = GONE
    }

}