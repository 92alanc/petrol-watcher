package com.braincorp.petrolwatcher.feature.auth

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.braincorp.petrolwatcher.BaseActivityTest
import com.braincorp.petrolwatcher.feature.auth.imageHandler.MockImageHandler
import com.braincorp.petrolwatcher.feature.auth.robots.profile
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest : BaseActivityTest<ProfileActivity>(ProfileActivity::class.java) {

    @Rule
    @JvmField
    val cameraPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(CAMERA)

    @Rule
    @JvmField
    val readExternalStoragePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(READ_EXTERNAL_STORAGE)

    @Rule
    @JvmField
    val writeExternalStoragePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(WRITE_EXTERNAL_STORAGE)

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

    private fun setUploadSuccess(uploadSuccess: Boolean) {
        (getImageHandler() as MockImageHandler).uploadSuccess = uploadSuccess
    }

}