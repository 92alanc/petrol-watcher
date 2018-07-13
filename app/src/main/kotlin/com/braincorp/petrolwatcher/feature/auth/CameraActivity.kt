package com.braincorp.petrolwatcher.feature.auth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.wonderkiln.camerakit.CameraKitEventCallback
import com.wonderkiln.camerakit.CameraKitImage
import kotlinx.android.synthetic.main.activity_camera.*

/**
 * The activity where photos are taken
 */
class CameraActivity : AppCompatActivity(), CameraKitEventCallback<CameraKitImage> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setupCameraButton()
    }

    override fun onResume() {
        super.onResume()
        camera_view.start()
    }

    override fun onPause() {
        camera_view.stop()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }

    override fun callback(image: CameraKitImage?) {
        //val result = BitmapFactory.decodeByteArray(image, 0, image?.length)
    }

    private fun setupCameraButton() {
        fab.setOnClickListener {
            camera_view.captureImage()
        }
    }

}