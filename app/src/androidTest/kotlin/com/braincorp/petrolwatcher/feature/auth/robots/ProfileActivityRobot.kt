package com.braincorp.petrolwatcher.feature.auth.robots

import android.content.Intent.ACTION_PICK
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.sentIntent
import com.braincorp.petrolwatcher.R

fun profile(func: ProfileActivityRobot.() -> Unit) = ProfileActivityRobot().apply(func)

class ProfileActivityRobot {

    infix fun clickCamera(func: ProfileResult.() -> Unit) {
        click {
            id(R.id.bt_camera)
        }

        applyResult(func)
    }

    infix fun clickGallery(func: ProfileResult.() -> Unit) {
        click {
            id(R.id.bt_gallery)
        }

        applyResult(func)
    }

    private fun applyResult(func: ProfileResult.() -> Unit) {
        ProfileResult().apply(func)
    }

}

class ProfileResult {

    fun cameraIsOpen() {
        sentIntent {
            action(ACTION_IMAGE_CAPTURE)
        }
    }

    fun galleryIsOpen() {
        sentIntent {
            action(ACTION_PICK)
            url(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
    }

}