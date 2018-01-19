package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<AuthResult> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        checkAuthenticationState()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignIn -> signIn()
            R.id.buttonSignUp -> startProfileActivity()
        }
    }

    override fun onComplete(task: Task<AuthResult>) {
        hideProgressBar()
        if (task.isSuccessful)
            startHomeActivity()
        else
            showErrorDialogue(R.string.invalid_email_or_password)
    }

    private fun checkAuthenticationState() {
        if (AuthenticationManager.USER != null)
            startHomeActivity()
    }

    private fun clearEditTexts() {
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

    private fun signIn() {
        showProgressBar()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        AuthenticationManager.signIn(email, password, onCompleteListener = this)
    }

    private fun startHomeActivity() {
        val intent = HomeActivity.getIntent(context = this)
        startActivity(intent)
        clearEditTexts()
    }

    private fun startProfileActivity() {
        val intent = ProfileActivity.getIntent(context = this)
        startActivity(intent)
        clearEditTexts()
    }

    private fun setOnClickListeners() {
        buttonSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

}
