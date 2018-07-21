package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.presenter.ProfilePresenter
import com.braincorp.petrolwatcher.feature.auth.utils.fillImageView
import com.braincorp.petrolwatcher.utils.dependencyInjection
import com.braincorp.petrolwatcher.utils.startVehicleListActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

/**
 * The activity where profiles can be created, viewed,
 * edited and deleted
 */
class ProfileActivity : AppCompatActivity(), View.OnClickListener, ProfileContract.View {

    private companion object {
        const val REQUEST_CODE_CAMERA = 3892
        const val REQUEST_CODE_GALLERY = 13797
    }

    override lateinit var presenter: ProfileContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter = ProfilePresenter(view = this,
                imageHandler = dependencyInjection().getImageHandler())
        setupButtons()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                val uri = presenter.getImageUriFromCameraIntent(data, this)
                fillImageView(uri, img_profile,
                        R.drawable.ic_profile, progress_bar)
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                val uri = presenter.getImageUriFromGalleryIntent(data)
                fillImageView(uri, img_profile, R.drawable.ic_profile, progress_bar)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val allPermissionsGranted = grantResults.all { it == PERMISSION_GRANTED }
        if (requestCode == REQUEST_CODE_CAMERA && allPermissionsGranted)
            presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)
    }

    /**
     * Shows an error dialogue
     *
     * @param messageId the error message ID
     */
    override fun showErrorDialogue(@StringRes messageId: Int) {
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
        // TODO: show map activity
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_camera -> presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)
            R.id.bt_gallery -> presenter.openGallery(activity = this, requestCode = REQUEST_CODE_GALLERY)
            R.id.bt_vehicles -> startVehicleListActivity(finishCurrent = true)
            R.id.fab -> presenter.saveProfile(img_profile.drawable, edt_name.text.toString(), context = this)
        }
    }

    private fun setupButtons() {
        bt_camera.setOnClickListener(this)
        bt_gallery.setOnClickListener(this)
        bt_vehicles.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

}