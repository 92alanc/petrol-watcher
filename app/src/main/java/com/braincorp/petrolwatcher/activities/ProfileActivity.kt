package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.storage.StorageManager
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.UserProfileChangeRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<Void> {

    companion object {
        private const val EXTRA_NEW_ACCOUNT = "new_account"

        fun getIntent(context: Context,
                      newAccount: Boolean = false): Intent {
            return Intent(context, ProfileActivity::class.java)
                    .putExtra(EXTRA_NEW_ACCOUNT, newAccount)
        }
    }

    private var editMode = false
    private var newAccountMode = false
    private var viewMode = true
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setOnClickListeners()
        parseIntent()
    }

    override fun onStart() {
        super.onStart()
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
                        Picasso.with(this).load(photoUri)
                                .placeholder(R.drawable.ic_profile)
                                .into(imageViewProfile)
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
                        Picasso.with(this).load(photoUri)
                                .placeholder(R.drawable.ic_profile)
                                .into(imageViewProfile)
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
        if (editMode) {
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
                if (viewMode) prepareEditMode()
                else save()
            }

            R.id.imageViewProfile -> {
                showImagePickerDialogue(cameraButtonAction = {
                    openCamera()
                }, galleryButtonAction = {
                    openGallery()
                })
            }
        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) {
            val intent = if (editMode) {
                HomeActivity.getIntent(context = this)
            } else {
                LoginActivity.getIntent(context = this)
            }
            startActivity(intent)
        }
    }

    private fun parseIntent() {
        newAccountMode = intent.getBooleanExtra(EXTRA_NEW_ACCOUNT, false)
    }

    private fun prepareEditMode() {
        editMode = true
        viewMode = false

        imageViewProfile.isClickable = true

        textViewDisplayName.visibility = GONE
        textViewEmail.visibility = GONE

        editTextDisplayName.visibility = VISIBLE
        editTextDisplayName.setText(AuthenticationManager.USER?.displayName)

        fabProfile.setImageResource(R.drawable.ic_save)
    }

    private fun prepareNewAccountMode() {
        viewMode = false
        editMode = false

        imageViewProfile.isClickable = true

        textViewDisplayName.visibility = GONE
        textViewEmail.visibility = GONE

        editTextDisplayName.visibility = VISIBLE
        editTextEmail.visibility = VISIBLE
        editTextPassword.visibility = VISIBLE
        editTextConfirmPassword.visibility = VISIBLE

        fabProfile.setImageResource(R.drawable.ic_save)
    }

    private fun prepareViewMode() {
        imageViewProfile.isClickable = false
        Picasso.with(this).load(AuthenticationManager.USER?.photoUrl)
                .placeholder(R.drawable.ic_profile)
                .into(imageViewProfile)

        textViewDisplayName.text = AuthenticationManager.USER?.displayName
        textViewEmail.text = AuthenticationManager.USER?.email
    }

    private fun prepareUi() {
        when {
            newAccountMode -> prepareNewAccountMode()
            editMode -> prepareEditMode()
            viewMode -> prepareViewMode()
        }
    }

    private fun save() {
        if (editMode) {
            val displayName = editTextDisplayName.text.toString()

            AuthenticationManager.USER?.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(photoUri)
                    .build())?.addOnCompleteListener(this)
        } else if (newAccountMode) {
            val displayName = editTextDisplayName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (password != confirmPassword) {
                showErrorDialogue(R.string.password_and_confirmation_dont_match)
            } else {
                AuthenticationManager.createUser(email, password, displayName, photoUri, {
                    showInformationDialogue(title = R.string.account_created,
                            message = R.string.confirmation_email)
                    val intent = LoginActivity.getIntent(context = this)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }

    private fun setOnClickListeners() {
        fabProfile.setOnClickListener(this)
        imageViewProfile.setOnClickListener(this)
    }

}
