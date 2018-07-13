package com.braincorp.petrolwatcher.feature.auth

import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import org.mockito.Mock
import org.mockito.Mockito.mock

class MockAuthenticator : Authenticator {

    @Mock
    private val result = mock(AuthResult::class.java)

    private val googleSuccessResult = GoogleSignInResult(googleAccount(), Status(200))

    private val googleFailureResult = GoogleSignInResult(googleAccount(), Status(13797))

    /**
     * Signs in with e-mail and password
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signIn(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        if (email == "test123@test.com" && password == "abcd1234")
            onSuccessListener.onSuccess(result)
        else
            onFailureListener.onFailure(Exception())
    }

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signUp(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        if (email == "test123@test.com" && password == "abcd1234")
            onSuccessListener.onSuccess(result)
        else
            onFailureListener.onFailure(Exception())
    }

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param requestCode the request code
     * @param onConnectionFailedListener the listener for connection
     *                                   failure events
     */
    override fun signInWithGoogle(activity: AppCompatActivity, requestCode: Int,
                                  onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener,
                                  resultCallback: ResultCallback<GoogleSignInResult>) {
        // FIXME
        if (requestCode == 3892)
            resultCallback.onResult(googleSuccessResult)
        else
            resultCallback.onResult(googleFailureResult)
    }

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     * @param callback the Facebook async authentication response
     *                 handler
     * @param callbackManager the Facebook async
     *                        authentication response manager
     */
    override fun signInWithFacebook(activity: AppCompatActivity,
                                    callback: FacebookCallback<LoginResult>,
                                    callbackManager: CallbackManager) {
        // TODO: implement
    }

    private fun googleAccount(): GoogleSignInAccount = GoogleSignInAccount.createDefault()

}