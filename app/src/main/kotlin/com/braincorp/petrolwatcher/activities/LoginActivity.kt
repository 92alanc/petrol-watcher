package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.isEmpty
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.utils.launchMapActivity
import com.braincorp.petrolwatcher.utils.launchProfileActivity
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
            R.id.buttonSignUp -> {
                launchProfileActivity(uiMode = AdaptableUi.Mode.CREATE, finishCurrent = false)
                clearEditTexts()
            }
        }
    }

    private fun checkAuthenticationState() {
        if (AuthenticationManager.isSignedIn() && AuthenticationManager.isEmailVerified())
            launchMapActivity(finishCurrent = false)
    }

    private fun clearEditTexts() {
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

    private fun signIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        showProgressBar()

        if (!isEmpty(email) && !isEmpty(password)) {
            AuthenticationManager.signIn(email, password, onSuccessAction = {
                runOnUiThread {
                    hideProgressBar()
                }
                if (it.user.isEmailVerified) {
                    launchMapActivity(finishCurrent = false)
                } else {
                    showInformationDialogue(title = R.string.email_not_verified,
                            message = R.string.verify_email)
                }
            }, onFailureAction = {
                runOnUiThread {
                    hideProgressBar()
                }
                showErrorDialogue(R.string.invalid_email_or_password)
            })
        } else {
            runOnUiThread {
                hideProgressBar()
            }
            showErrorDialogue(R.string.invalid_email_or_password)
        }
    }

    private fun setOnClickListeners() {
        buttonSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
    }

    private fun showProgressBar() {
        groupLogin.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = GONE
        groupLogin.visibility = VISIBLE
    }

}
