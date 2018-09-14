package com.braincorp.petrolwatcher.feature.auth

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.*
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.authenticator.OnUserDataFoundListener
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.presenter.ProfilePresenter
import com.braincorp.petrolwatcher.utils.hasExternalStoragePermission
import com.braincorp.petrolwatcher.utils.startMapActivity
import com.braincorp.petrolwatcher.utils.startVehicleListActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

/**
 * The activity where profiles can be created, viewed,
 * edited and deleted
 */
class ProfileActivity : AppCompatActivity(), View.OnClickListener,
        ProfileContract.View, OnUserDataFoundListener {

    companion object {
        private const val KEY_EDIT_MODE = "edit_mode"
        private const val KEY_NAME = "name"
        private const val KEY_PROFILE_PICTURE = "profile_picture"
        private const val REQUEST_CODE_CAMERA = 3892
        private const val REQUEST_CODE_GALLERY = 13797
        private const val TAG = "PETROL_WATCHER"

        fun intent(context: Context, editMode: Boolean): Intent {
            return Intent(context, ProfileActivity::class.java)
                    .putExtra(KEY_EDIT_MODE, editMode)
        }
    }

    override lateinit var presenter: ProfileContract.Presenter

    private val authenticator = DependencyInjection.authenticator
    private val imageHandler = DependencyInjection.imageHandler

    private var editMode = false
    private var name: String? = ""
    private var profilePicture: Uri? = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter = ProfilePresenter(view = this, imageHandler = imageHandler)
        setupButtons()
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        else
            editMode = intent.getBooleanExtra(KEY_EDIT_MODE, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putBoolean(KEY_EDIT_MODE, editMode)
            putString(KEY_NAME, name)
            putParcelable(KEY_PROFILE_PICTURE, profilePicture)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (editMode)
            updateWithUserData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                profilePicture = presenter.getImageUriFromCameraIntent(data, this)
                Log.d(TAG, "URI from camera: $profilePicture")
                imageHandler.fillImageView(profilePicture, img_profile,
                                           R.drawable.ic_profile, progress_bar)
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                profilePicture = presenter.getImageUriFromGalleryIntent(data)
                Log.d(TAG, "URI from gallery: $profilePicture")
                imageHandler.fillImageView(profilePicture, img_profile,
                                           R.drawable.ic_profile, progress_bar)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val allPermissionsGranted = grantResults.all { it == PERMISSION_GRANTED }

        if (requestCode == REQUEST_CODE_CAMERA && allPermissionsGranted)
            presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)

        if (requestCode == REQUEST_CODE_GALLERY && allPermissionsGranted)
            presenter.openGallery(activity = this, requestCode = REQUEST_CODE_GALLERY)
    }

    /**
     * Shows an error dialogue
     *
     * @param messageId the error message ID
     */
    override fun showErrorDialogue(@StringRes messageId: Int) {
        showCardViewAndVehiclesButton()

        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setIcon(R.drawable.ic_error)
                .setMessage(messageId)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    /**
     * Shows the map activity
     */
    override fun showMap() {
        showCardViewAndVehiclesButton()
        startMapActivity()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_camera -> presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)
            R.id.bt_gallery -> presenter.openGallery(activity = this, requestCode = REQUEST_CODE_GALLERY)
            R.id.bt_vehicles -> startVehicleListActivity(finishCurrent = true)
            R.id.fab -> saveProfile()
        }
    }

    /**
     * Function triggered when user data is found
     *
     * @param displayName the display name
     * @param profilePictureUri the profile picture URI
     */
    override fun onUserDataFound(displayName: String?, profilePictureUri: Uri?) {
        name = displayName
        profilePicture = profilePictureUri
        edt_name.setText(displayName)
        imageHandler.fillImageView(profilePictureUri, img_profile, progressBar = progress_bar)
    }

    private fun setupButtons() {
        bt_camera.setOnClickListener(this)
        bt_gallery.setOnClickListener(this)
        bt_vehicles.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

    private fun updateWithUserData() {
        authenticator.getUserData(this)
    }

    private fun saveProfile() {
        card_view.visibility = INVISIBLE // Workaround
        bt_vehicles.visibility = GONE
        progress_bar.visibility = VISIBLE

        if (SDK_INT >= M) {
            if (hasExternalStoragePermission()) {
                presenter.saveProfile(img_profile.drawable, edt_name.text.toString(), context = this)
            } else {
                requestPermissions(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                                   REQUEST_CODE_GALLERY)
            }
        } else {
            presenter.saveProfile(img_profile.drawable, edt_name.text.toString(), context = this)
        }
    }

    private fun showCardViewAndVehiclesButton() {
        progress_bar.visibility = GONE
        card_view.visibility = VISIBLE
        bt_vehicles.visibility = VISIBLE
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            editMode = getBoolean(KEY_EDIT_MODE)
            name = getString(KEY_NAME)
            profilePicture = getParcelable(KEY_PROFILE_PICTURE)
        }
    }

}