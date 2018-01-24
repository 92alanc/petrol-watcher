package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.fragments.DisplayNameFragment
import com.braincorp.petrolwatcher.fragments.EmailAndPasswordFragment
import com.braincorp.petrolwatcher.fragments.ImagePickerFragment
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.storage.StorageManager
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<Void> {

    companion object {
        private const val EXTRA_MODE = "mode"
        private const val TAG_EMAIL_PASSWORD = "email_password"
        private const val TAG_NAME = "name"

        fun getIntent(context: Context,
                      uiMode: UiMode): Intent {
            return Intent(context, ProfileActivity::class.java)
                    .putExtra(EXTRA_MODE, uiMode)
        }
    }

    private lateinit var uiMode: UiMode

    private var bottomFragment: Fragment? = null
    private var topFragment: ImagePickerFragment? = null
    private var photoUri: Uri? = null
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        fabProfile.setOnClickListener(this)
        parseIntent()
        prepareUi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    StorageManager.upload(bitmap, onSuccessAction = {
                        photoUri = it.downloadUrl
                        topFragment?.setData(photoUri)
                    }, onFailureAction = {
                        showErrorDialogue(R.string.error_setting_profile_picture)
                    })
                }
            }

            REQUEST_CODE_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    photoUri = data?.data
                    StorageManager.upload(photoUri!!, onSuccessAction = {
                        photoUri = it.downloadUrl
                        topFragment?.setData(photoUri)
                    }, onFailureAction = {
                        showErrorDialogue(R.string.error_setting_profile_picture)
                    })
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA && grantResults[0] == PERMISSION_GRANTED)
            openCamera()
    }

    override fun onBackPressed() {
        if (uiMode == UiMode.EDIT) {
            showQuestionDialogue(title = R.string.changes_not_saved,
                    message = R.string.question_changes_not_saved,
                    positiveFunc = {
                        super.onBackPressed()
                    },
                    negativeFunc = { })
        } else super.onBackPressed()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabProfile -> {
                when (uiMode) {
                    UiMode.CREATE -> {
                        if (bottomFragment?.tag == TAG_EMAIL_PASSWORD) prepareLastStep()
                        else save()
                    }

                    UiMode.EDIT -> save()
                    UiMode.VIEW -> prepareEditMode()
                }
            }
        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) {
            val intent = if (uiMode == UiMode.EDIT) HomeActivity.getIntent(context = this)
            else LoginActivity.getIntent(context = this)
            startActivity(intent)
        }
    }

    private fun bindFragments(bottomFragmentTag: String) {
        if (topFragment != null) {
            replaceFragmentPlaceholder(R.id.placeholderProfileTop, topFragment!!)
        }
        if (bottomFragment != null) {
            replaceFragmentPlaceholder(R.id.placeholderProfileBottom, bottomFragment!!,
                    bottomFragmentTag)
        }
    }

    private fun parseIntent() {
        uiMode = intent.getSerializableExtra(EXTRA_MODE) as UiMode
    }

    private fun prepareEditMode() {
        uiMode = UiMode.EDIT

        textViewProfileHeader.visibility = GONE

        fabProfile.setImageResource(R.drawable.ic_save)

        topFragment = ImagePickerFragment.newInstance(uiMode)
        bottomFragment = DisplayNameFragment.newInstance(uiMode)

        bindFragments(TAG_NAME)
    }

    private fun prepareNewAccountMode() {
        textViewProfileHeader.visibility = VISIBLE
        textViewProfileHeader.setText(R.string.header_email_password)

        fabProfile.setImageResource(R.drawable.ic_next)

        topFragment = null
        bottomFragment = EmailAndPasswordFragment.newInstance(uiMode)

        bindFragments(TAG_EMAIL_PASSWORD)
    }

    private fun prepareViewMode() {
        textViewProfileHeader.visibility = GONE

        fabProfile.setImageResource(R.drawable.ic_edit)

        topFragment = ImagePickerFragment.newInstance(uiMode)
        bottomFragment = DisplayNameFragment.newInstance(uiMode)

        bindFragments(TAG_NAME)
    }

    private fun prepareLastStep() {
        val email = (bottomFragment as EmailAndPasswordFragment).getEmail()
        val password = (bottomFragment as EmailAndPasswordFragment).getPassword()
        val passwordConfirmation = (bottomFragment as EmailAndPasswordFragment).getPasswordConfirmation()

        if (password != passwordConfirmation) {
            showErrorDialogue(R.string.password_and_confirmation_dont_match)
        } else {
            AuthenticationManager.createUser(email, password, {
                if (it.isSuccessful) {
                    AuthenticationManager.signIn(email, password, onSuccessAction = {
                        user = it.user
                        textViewProfileHeader.setText(R.string.header_picture_name)

                        topFragment = ImagePickerFragment.newInstance(uiMode)
                        bottomFragment = DisplayNameFragment.newInstance(uiMode)

                        fabProfile.setImageResource(R.drawable.ic_save)
                        bindFragments(TAG_NAME)
                    }, onFailureAction = {
                        showErrorDialogue(R.string.error_signing_in)
                    })
                } else {
                    showErrorDialogue(R.string.error_creating_account)
                }
            })
        }
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareNewAccountMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun save() {
        val displayName = (bottomFragment as DisplayNameFragment).getDisplayName()
        val profilePicture = topFragment?.getImageUri()

        AuthenticationManager.setDisplayNameAndProfilePicture(user, displayName, profilePicture,
                onSuccessAction =  {
                    val intent = if (uiMode == UiMode.CREATE)
                        LoginActivity.getIntent(context = this)
                    else
                        HomeActivity.getIntent(context = this)
                    startActivity(intent)
                    finish()
                }, onFailureAction = {
            showErrorDialogue(R.string.error_setting_profile_picture)
        })
    }

}
