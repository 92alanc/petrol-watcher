package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.braincorp.petrolwatcher.R

class UserBasicInfoFragment : Fragment() {

    companion object {
        private const val ARG_NEW_ACCOUNT = "new_account"

        fun newInstance(newAccount: Boolean = false): UserBasicInfoFragment {
            val instance = UserBasicInfoFragment()
            val args = Bundle()
            args.putBoolean(ARG_NEW_ACCOUNT, newAccount)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewDisplayName: TextView
    private lateinit var textViewEmail: TextView

    private lateinit var editTextDisplayName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    private var editMode = false
    private var newAccount = false
    private var viewMode = true

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_basic_info, container, false)
        parseArgs()
        bindViews(view)
        prepareUi()
        return view
    }

    private fun bindViews(view: View) {
        textViewDisplayName = view.findViewById(R.id.textViewDisplayName)
        textViewEmail = view.findViewById(R.id.textViewEmail)

        editTextDisplayName = view.findViewById(R.id.editTextDisplayName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        editTextPassword = view.findViewById(R.id.editTextPassword)
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
    }

    private fun parseArgs() {
        newAccount = arguments?.getBoolean(ARG_NEW_ACCOUNT, false)!!
    }

    private fun prepareUi() {

    }

}