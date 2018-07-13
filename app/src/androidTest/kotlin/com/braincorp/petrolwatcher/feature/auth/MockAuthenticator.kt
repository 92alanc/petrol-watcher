package com.braincorp.petrolwatcher.feature.auth

import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import org.mockito.Mock
import org.mockito.Mockito.mock

class MockAuthenticator : Authenticator {

    @Mock
    private val result = mock(AuthResult::class.java)

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

}