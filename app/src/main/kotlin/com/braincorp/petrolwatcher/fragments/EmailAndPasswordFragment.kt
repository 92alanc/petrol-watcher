package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.constraint.Group
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

class EmailAndPasswordFragment : Fragment(), AdaptableUi {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: AdaptableUi.Mode): EmailAndPasswordFragment {
            val instance = EmailAndPasswordFragment()
            val args = Bundle()
            args.putSerializable(ARG_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private val user = FirebaseAuth.getInstance().currentUser

    private lateinit var textViewEmail: TextView

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    private lateinit var groupPasswordFields: Group

    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_email_password, container, false)
        parseArgs()
        bindViews(view)
        prepareUi()
        return view
    }

    fun getEmail(): String = editTextEmail.text.toString()

    fun getPassword(): String = editTextPassword.text.toString()

    fun getPasswordConfirmation(): String = editTextConfirmPassword.text.toString()

    private fun bindViews(view: View) {
        textViewEmail = view.findViewById(R.id.textViewEmail)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)

        groupPasswordFields = view.findViewById(R.id.groupPasswordFields)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE)!! as AdaptableUi.Mode
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        textViewEmail.visibility = GONE
        editTextEmail.visibility = VISIBLE
        groupPasswordFields.visibility = VISIBLE
    }

    override fun prepareEditMode() {
        prepareViewMode()
    }

    override fun prepareViewMode() {
        textViewEmail.visibility = VISIBLE
        textViewEmail.text = user?.email

        editTextEmail.visibility = GONE
        groupPasswordFields.visibility = GONE
    }

}