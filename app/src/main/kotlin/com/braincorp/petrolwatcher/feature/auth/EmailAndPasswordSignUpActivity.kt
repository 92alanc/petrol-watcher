package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailAndPasswordSignUpPresenter
import com.braincorp.petrolwatcher.utils.startProfileActivity
import kotlinx.android.synthetic.main.activity_email_and_password.*
import kotlinx.android.synthetic.main.content_email_and_password.*

/**
 * The activity where e-mail and password based
 * accounts are created
 */
class EmailAndPasswordSignUpActivity : AppCompatActivity(), EmailAndPasswordSignUpContract.View {

    override val presenter: EmailAndPasswordSignUpContract.Presenter = EmailAndPasswordSignUpPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_and_password)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab_next.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            val confirmation = edt_password_confirmation.text.toString()
            presenter.createAccount(email, password, confirmation)
        }
    }

    /**
     * Shows an error when the password confirmation
     * field is empty
     */
    override fun showEmptyConfirmationError() {
        edt_password_confirmation.error = getString(R.string.error_empty_confirmation)
    }

    /**
     * Shows an error when the e-mail field is empty
     */
    override fun showEmptyEmailError() {
        edt_email.error = getString(R.string.error_empty_email)
    }

    /**
     * Shows an error when the password field is empty
     */
    override fun showEmptyPasswordError() {
        edt_password.error = getString(R.string.error_empty_password)
    }

    /**
     * Shows an error when the password and confirmation
     * don't match
     */
    override fun showPasswordNotMatchingError() {
        edt_password_confirmation.error = getString(R.string.error_password_and_confirmation_dont_match)
    }

    /**
     * Shows the profile
     */
    override fun showProfile() {
        startProfileActivity(finishCurrent = true)
    }

}