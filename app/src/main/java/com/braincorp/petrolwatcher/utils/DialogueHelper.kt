package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.view.ImagePickerDialogue

fun Context.showErrorDialogue(@StringRes message: Int) {
    AlertDialog.Builder(this).setTitle(R.string.error)
            .setMessage(message)
            .setIcon(R.drawable.ic_error)
            .setNeutralButton(R.string.ok, null)
            .show()
}

fun Context.showImagePickerDialogue(cameraButtonAction: () -> Unit,
                                    galleryButtonAction: () -> Unit) {
    val dialogue = ImagePickerDialogue(context = this)
    dialogue.setOnShowListener({
        dialogue.setCameraClickAction(cameraButtonAction)
        dialogue.setGalleryClickAction(galleryButtonAction)
    })
    dialogue.show()
}

fun Context.showInformationDialogue(@StringRes title: Int,
                                    @StringRes message: Int) {
    AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_information)
            .setNeutralButton(R.string.ok, null)
            .show()
}

fun Context.showQuestionDialogue(@StringRes title: Int,
                                 @StringRes message: Int,
                                 positiveFunc: () -> Unit,
                                 negativeFunc: () -> Unit) {
    AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_question)
            .setPositiveButton(R.string.yes) { _, _ ->
                positiveFunc()
            }
            .setNegativeButton(R.string.no, { _, _ ->
                negativeFunc()
            })
            .show()
}