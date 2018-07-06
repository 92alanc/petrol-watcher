package com.braincorp.petrolwatcher.feature.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import kotlinx.android.synthetic.main.activity_authentication_error.*

/**
 * The activity where authentication errors are shown
 */
class AuthenticationErrorActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ERROR_TYPE = "error_type"

        fun getIntent(context: Context, errorType: AuthErrorType): Intent {
            return Intent(context, AuthenticationErrorActivity::class.java)
                    .putExtra(EXTRA_ERROR_TYPE, errorType)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_error)
        bt_try_again.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        when (getErrorType()) {
            AuthErrorType.CONNECTION -> showConnectionError()
            AuthErrorType.EMAIL_PASSWORD -> showEmailAndPasswordError()
            AuthErrorType.FACEBOOK -> showFacebookError()
            AuthErrorType.GOOGLE -> showGoogleError()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun showConnectionError() {
        img_error.setImageResource(R.drawable.ic_disconnected)
        txt_error_description.setText(R.string.error_connection)
    }

    private fun showEmailAndPasswordError() {
        img_error.setImageResource(R.drawable.ic_error)
        txt_error_description.setText(R.string.error_email_password)
    }

    private fun showFacebookError() {
        img_error.setImageResource(R.drawable.ic_error)
        txt_error_description.setText(R.string.error_facebook)
    }

    private fun showGoogleError() {
        img_error.setImageResource(R.drawable.ic_error)
        txt_error_description.setText(R.string.error_google)
    }

    private fun getErrorType(): AuthErrorType {
        return intent.getSerializableExtra(EXTRA_ERROR_TYPE) as AuthErrorType
    }

}