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

class EmailAndPasswordFragment : Fragment() {

    companion object {
        private const val ARG_MODE = "mode"

        fun newInstance(uiMode: UiMode): EmailAndPasswordFragment {
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

    private var uiMode = UiMode.VIEW

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

    fun setUiMode(uiMode: UiMode) {
        this.uiMode = uiMode
        prepareUi()
    }

    private fun bindViews(view: View) {
        textViewEmail = view.findViewById(R.id.textViewEmail)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_MODE)!! as UiMode
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareNewAccountMode()
            else -> prepareViewMode()
        }
    }

    private fun prepareNewAccountMode() {
        textViewEmail.visibility = GONE

        editTextEmail.visibility = VISIBLE
        editTextPassword.visibility = VISIBLE
        editTextConfirmPassword.visibility = VISIBLE
    }

    private fun prepareViewMode() {
        textViewEmail.visibility = VISIBLE
        textViewEmail.text = user?.email

        editTextEmail.visibility = GONE
        editTextPassword.visibility = GONE
        editTextConfirmPassword.visibility = GONE
    }

}