package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.ProfileContract
import com.braincorp.petrolwatcher.feature.auth.presenter.ProfilePresenter
import com.braincorp.petrolwatcher.utils.fillImageView
import com.braincorp.petrolwatcher.utils.toUri
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

    override val presenter: ProfileContract.Presenter = ProfilePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupButtons()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                val bitmap = data?.extras?.get("data") as Bitmap
                fillImageView(bitmap.toUri(this), img_profile,
                        R.drawable.ic_profile, progress_bar)
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                val uri = data?.data
                fillImageView(uri, img_profile, R.drawable.ic_profile, progress_bar)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val cameraResult = grantResults[0]
        if (requestCode == REQUEST_CODE_CAMERA && cameraResult == PERMISSION_GRANTED)
            presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_camera -> presenter.openCamera(activity = this, requestCode = REQUEST_CODE_CAMERA)
            R.id.bt_gallery -> presenter.openGallery(activity = this, requestCode = REQUEST_CODE_GALLERY)
            R.id.fab -> TODO()
        }
    }

    private fun setupButtons() {
        bt_camera.setOnClickListener(this)
        bt_gallery.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

}