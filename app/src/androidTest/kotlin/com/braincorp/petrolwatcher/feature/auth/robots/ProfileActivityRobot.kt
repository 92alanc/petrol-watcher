package com.braincorp.petrolwatcher.feature.auth.robots

import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.actions.TextActions.typeText
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.stubIntent
import com.braincorp.petrolwatcher.R

fun profile(func: ProfileActivityRobot.() -> Unit) = ProfileActivityRobot().apply(func)

class ProfileActivityRobot {

    fun typeName(name: String) {
        typeText(name) {
            id(R.id.edt_name)
        }
    }

    fun showDefaultPicture() {
        displayed {
            image(R.drawable.ic_profile)
        }
    }

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

    infix fun clickSave(func: ProfileResult.() -> Unit) {
        click {
            id(R.id.fab)
        }

        applyResult(func)
    }

    infix fun clickMyVehicles(func: ProfileResult.() -> Unit) {
        click {
            id(R.id.bt_vehicles)
        }

        applyResult(func)
    }

    private fun applyResult(func: ProfileResult.() -> Unit) {
        ProfileResult().apply(func)
    }

}

class ProfileResult {

    fun cameraIsOpen() {
        stubIntent {
            respondWith {
                ok()
                customCode(3892)
            }
        }
    }

    fun galleryIsOpen() {
        stubIntent {
            respondWith {
                ok()
                customCode(13797)
            }
        }
    }

    fun redirectToMapActivity() {
        // TODO: implement
    }

    fun showErrorDialogue() {
        displayed {
            text(R.string.error_profile_picture_display_name)
        }
    }

    fun redirectToVehicleListActivity() {
        // TODO: implement
    }

}