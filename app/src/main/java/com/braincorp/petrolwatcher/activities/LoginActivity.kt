package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.isEmpty
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.braincorp.petrolwatcher.utils.showInformationDialogue
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

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

    private fun checkAuthenticationState() {
        if (AuthenticationManager.isSignedIn() && AuthenticationManager.isEmailVerified())
            startMapActivity()
    }

    private fun clearEditTexts() {
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

    private fun signIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        if (!isEmpty(email) && !isEmpty(password)) {
            AuthenticationManager.signIn(email, password, onSuccessAction = {
                if (it.user.isEmailVerified) {
                    startMapActivity()
                } else {
                    showInformationDialogue(title = R.string.email_not_verified,
                            message = R.string.verify_email)
                }
            }, onFailureAction = {
                showErrorDialogue(R.string.invalid_email_or_password)
            })
        } else {
            showErrorDialogue(R.string.invalid_email_or_password)
        }
    }

    private fun startMapActivity() {
        val intent = MapActivity.getIntent(context = this)
        startActivity(intent)
    }

    private fun startProfileActivity() {
        val intent = ProfileActivity.getIntent(context = this,
                uiMode = AdaptableUi.Mode.CREATE)
        startActivity(intent)
        clearEditTexts()
    }

    private fun setOnClickListeners() {
        buttonSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

}
