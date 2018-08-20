package com.braincorp.petrolwatcher.feature.auth

import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.base.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.imageHandler.MockImageHandler
import com.braincorp.petrolwatcher.feature.auth.robots.profile
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest : BaseActivityTest<ProfileActivity>(ProfileActivity::class.java) {

    @Test
    fun whenClickingOnCamera_shouldOpenCamera() {
        profile {
        } clickCamera {
            cameraIsOpen()
        }
    }

    @Test
    fun whenClickingOnGallery_shouldOpenGallery() {
        profile {
        } clickGallery {
            galleryIsOpen()
        }
    }

    @Test
    fun whenSettingProfilePictureAndDisplayName_shouldRedirectToMapActivity() {
        setUploadSuccess(true)
        profile {
            typeName("Alan Camargo")
        } clickSave {
            redirectToMapActivity()
        }
    }

    @Test
    fun withUploadFailure_shouldShowErrorDialogue() {
        setUploadSuccess(false)
        profile {
        } clickSave {
            showErrorDialogue()
        }
    }

    @Test
    fun whenSettingProfilePictureAndDisplayName_withError_shouldShowErrorDialogue() {
        setUploadSuccess(true)
        profile {
            typeName("Error")
        } clickSave {
            showErrorDialogue()
        }
    }

    @Test
    fun withNoProfilePicture_shouldShowDefaultPicture() {
        profile {
            showDefaultPicture()
        }
    }

    @Test
    fun whenClickingOnMyVehicles_shouldRedirectToVehicleListActivity() {
        profile {
        } clickMyVehicles {
            redirectToVehicleListActivity()
        }
    }

    private fun setUploadSuccess(uploadSuccess: Boolean) {
        (DependencyInjection.imageHandler as MockImageHandler).uploadSuccess = uploadSuccess
    }

}