package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.braincorp.petrolwatcher.R

class ImagePickerDialogue(context: Context) : Dialog(context) {

    private lateinit var buttonCamera: Button
    private lateinit var buttonGallery: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_image_picker)
        bindViews()
    }

    fun setCameraClickAction(cameraButtonAction: () -> Unit) {
        buttonCamera.setOnClickListener({
            dismiss()
            cameraButtonAction()
        })
    }

    fun setGalleryClickAction(galleryButtonAction: () -> Unit) {
        buttonGallery.setOnClickListener({
            dismiss()
            galleryButtonAction()
        })
    }

    private fun bindViews() {
        buttonCamera = findViewById(R.id.buttonCamera)
        buttonGallery = findViewById(R.id.buttonGallery)
    }

}