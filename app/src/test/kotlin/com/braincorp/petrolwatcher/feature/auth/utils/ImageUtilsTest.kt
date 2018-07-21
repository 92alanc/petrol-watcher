package com.braincorp.petrolwatcher.feature.auth.utils

import android.graphics.Bitmap
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class ImageUtilsTest {

    @Mock
    private val bitmap = mock(Bitmap::class.java)

    @Test
    fun shouldConvertBitmapToByteArray() {
        assertNotNull(bitmap.toByteArray())
    }

}