package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
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

        private const val KEY_UI_MODE = "ui_mode"
        private const val KEY_TOP_FRAGMENT = "top_fragment"
        private const val KEY_BOTTOM_FRAGMENT = "bottom_fragment"

        private const val TAG_PICTURE = "picture"
        private const val TAG_EMAIL_PASSWORD = "email_password"
        private const val TAG_NAME = "name"

        fun getIntent(context: Context,
                      uiMode: UiMode): Intent {
            return Intent(context, ProfileActivity::class.java)
                    .putExtra(EXTRA_MODE, uiMode)
        }
    }

    private lateinit var uiMode: UiMode

    private var topFragment: ImagePickerFragment? = null
    private var bottomFragment: Fragment? = null

    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        fabProfile.setOnClickListener(this)
        buttonVehicles.setOnClickListener(this)
        if (savedInstanceState != null)
            uiMode = savedInstanceState.getSerializable(KEY_UI_MODE) as UiMode
        else
            parseIntent()
        prepareUi(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    topFragment?.setImageBitmap(bitmap)
                }
            }

            REQUEST_CODE_GALLERY -> {
                if (resultCode == RESULT_OK)
                    topFragment?.setImageUri(data?.data)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA && grantResults[0] == PERMISSION_GRANTED)
            openCamera()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (topFragment != null) outState?.putString(KEY_TOP_FRAGMENT, topFragment!!.tag)
        if (bottomFragment != null) outState?.putString(KEY_BOTTOM_FRAGMENT, bottomFragment!!.tag)
        outState?.putSerializable(KEY_UI_MODE, uiMode)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val topFragmentTag = savedInstanceState?.getString(KEY_TOP_FRAGMENT)
        val bottomFragmentTag = savedInstanceState?.getString(KEY_BOTTOM_FRAGMENT)

        if (topFragmentTag != null)
            topFragment = supportFragmentManager.findFragmentByTag(topFragmentTag) as ImagePickerFragment

        if (bottomFragmentTag != null)
            bottomFragment = supportFragmentManager.findFragmentByTag(bottomFragmentTag)

        uiMode = savedInstanceState?.getSerializable(KEY_UI_MODE) as UiMode
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
                    UiMode.VIEW -> prepareEditMode(savedInstanceState = null)
                }
            }

            R.id.buttonVehicles -> {
                // TODO: open VehiclesActivity
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
            replaceFragmentPlaceholder(R.id.placeholderProfileTop, topFragment!!, TAG_PICTURE)
        }
        if (bottomFragment != null) {
            replaceFragmentPlaceholder(R.id.placeholderProfileBottom, bottomFragment!!,
                    bottomFragmentTag)
        }
    }

    private fun parseIntent() {
        uiMode = intent.getSerializableExtra(EXTRA_MODE) as UiMode
    }

    private fun prepareEditMode(savedInstanceState: Bundle?) {
        uiMode = UiMode.EDIT

        buttonVehicles.visibility = GONE

        textViewProfileHeader.visibility = GONE

        fabProfile.setImageResource(R.drawable.ic_save)

        topFragment = ImagePickerFragment.newInstance(uiMode)
        bottomFragment = DisplayNameFragment.newInstance(uiMode)

        if (savedInstanceState == null)
            bindFragments(TAG_NAME)
    }

    private fun prepareNewAccountMode(savedInstanceState: Bundle?) {
        textViewProfileHeader.visibility = VISIBLE
        textViewProfileHeader.setText(R.string.header_email_password)

        buttonVehicles.visibility = GONE

        fabProfile.setImageResource(R.drawable.ic_next)

        topFragment = null
        bottomFragment = EmailAndPasswordFragment.newInstance(uiMode)

        if (savedInstanceState == null)
            bindFragments(TAG_EMAIL_PASSWORD)
    }

    private fun prepareViewMode(savedInstanceState: Bundle?) {
        textViewProfileHeader.visibility = GONE

        buttonVehicles.visibility = VISIBLE

        fabProfile.setImageResource(R.drawable.ic_edit)

        topFragment = ImagePickerFragment.newInstance(uiMode)
        bottomFragment = DisplayNameFragment.newInstance(uiMode)

        if (savedInstanceState == null)
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

                        runOnUiThread {
                            textViewProfileHeader.setText(R.string.header_picture_name)
                            topFragment = ImagePickerFragment.newInstance(uiMode)
                            bottomFragment = DisplayNameFragment.newInstance(uiMode)

                            fabProfile.setImageResource(R.drawable.ic_save)
                            bindFragments(TAG_NAME)
                        }
                    }, onFailureAction = {
                        showErrorDialogue(R.string.error_signing_in)
                    })
                } else {
                    showErrorDialogue(R.string.error_creating_account)
                }
            })
        }
    }

    private fun prepareUi(savedInstanceState: Bundle?) {
        when (uiMode) {
            UiMode.CREATE -> prepareNewAccountMode(savedInstanceState)
            UiMode.EDIT -> prepareEditMode(savedInstanceState)
            UiMode.VIEW -> prepareViewMode(savedInstanceState)
        }
    }

    private fun save() {
        val displayName = (bottomFragment as DisplayNameFragment).getDisplayName()

        val bitmap = topFragment?.getImageBitmap()
        StorageManager.upload(bitmap, onSuccessAction = {
            AuthenticationManager.setDisplayNameAndProfilePicture(user, displayName, it.downloadUrl,
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
        }, onFailureAction = {
            showErrorDialogue(R.string.error_setting_profile_picture)
        })
    }

}
