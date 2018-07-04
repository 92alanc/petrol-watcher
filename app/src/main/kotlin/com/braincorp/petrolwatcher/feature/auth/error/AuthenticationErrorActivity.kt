package com.braincorp.petrolwatcher.feature.auth.error

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R

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

    private fun showConnectionError() {
        // TODO: implement
    }

    private fun showEmailAndPasswordError() {
        // TODO: implement
    }

    private fun showFacebookError() {
        // TODO: implement
    }

    private fun showGoogleError() {
        // TODO: implement
    }

    private fun getErrorType(): AuthErrorType {
        return intent.getSerializableExtra(EXTRA_ERROR_TYPE) as AuthErrorType
    }

}