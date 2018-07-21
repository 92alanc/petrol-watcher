package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailAndPasswordSignUpPresenter
import com.braincorp.petrolwatcher.utils.dependencyInjection
import com.braincorp.petrolwatcher.utils.showFieldError
import com.braincorp.petrolwatcher.utils.startProfileActivity
import kotlinx.android.synthetic.main.activity_email_and_password_sign_up.*
import kotlinx.android.synthetic.main.content_email_and_password_sign_up.*

/**
 * The activity where e-mail and password based
 * accounts are created
 */
class EmailAndPasswordSignUpActivity : AppCompatActivity(), EmailAndPasswordSignUpContract.View {

    override lateinit var presenter: EmailAndPasswordSignUpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_and_password_sign_up)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = EmailAndPasswordSignUpPresenter(view = this,
                authenticator = dependencyInjection().getAuthenticator())
        setupNextButton()
    }

    /**
     * Shows an error when the password confirmation
     * field is empty
     */
    override fun showEmptyConfirmationError() {
        showFieldError(edt_password_confirmation, getString(R.string.error_empty_confirmation))
    }

    /**
     * Shows an error when the password and confirmation
     * don't match
     */
    override fun showPasswordNotMatchingError() {
        showFieldError(edt_password_confirmation, getString(R.string.error_password_and_confirmation_dont_match))
    }

    /**
     * Shows an e-mail format error
     */
    override fun showEmailFormatError() {
        showFieldError(edt_email, getString(R.string.error_email_format))
    }

    /**
     * Shows a backend error dialogue
     */
    override fun showBackendError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setIcon(R.drawable.ic_error)
                .setMessage(R.string.error_creating_account)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    /**
     * Shows the profile
     */
    override fun showProfile() {
        startProfileActivity(finishCurrent = true)
    }

    /**
     * Shows a password length warning
     */
    override fun showPasswordLengthWarning() {
        AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(R.string.warning_password_length)
                .setIcon(R.drawable.ic_warning)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    private fun setupNextButton() {
        fab.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            val confirmation = edt_password_confirmation.text.toString()
            presenter.createAccount(email, password, confirmation)
        }
    }

}