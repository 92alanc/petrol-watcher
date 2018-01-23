package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<AuthResult> {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        checkAuthenticationState()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignIn -> signIn()
            R.id.buttonSignUp -> startProfileActivity()
        }
    }

    override fun onComplete(task: Task<AuthResult>) {
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
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        if (!isEmpty(email) && !isEmpty(password))
            AuthenticationManager.signIn(email, password, onCompleteListener = this)
        else
            showErrorDialogue(R.string.invalid_email_or_password)
    }

    private fun startHomeActivity() {
        val intent = HomeActivity.getIntent(context = this)
        startActivity(intent)
    }

    private fun startProfileActivity() {
        val intent = ProfileActivity.getIntent(context = this,
                                               edit = true)
        startActivity(intent)
        clearEditTexts()
    }

    private fun setOnClickListeners() {
        buttonSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

}
