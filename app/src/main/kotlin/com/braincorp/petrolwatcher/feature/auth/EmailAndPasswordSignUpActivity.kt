package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.EmailAndPasswordSignUpContract
import com.braincorp.petrolwatcher.feature.auth.presenter.EmailAndPasswordSignUpPresenter
import kotlinx.android.synthetic.main.activity_email_and_password.*
import kotlinx.android.synthetic.main.content_email_and_password.*

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
            presenter.validateCredentials(email, password, confirmation)
        }
    }

    override fun showEmptyConfirmationError() {
        edt_password_confirmation.error = getString(R.string.error_empty_confirmation)
    }

    override fun showEmptyEmailError() {
        edt_email.error = getString(R.string.error_empty_email)
    }

    override fun showEmptyPasswordError() {
        edt_password.error = getString(R.string.error_empty_password)
    }

    override fun showPasswordNotMatchingError() {
        edt_password_confirmation.error = getString(R.string.error_password_and_confirmation_dont_match)
    }

    override fun showProfile() {
        Toast.makeText(this, "Oh yeah!", Toast.LENGTH_SHORT).show()
        // TODO: redirect to profile activity
    }

}