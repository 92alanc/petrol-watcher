package com.braincorp.petrolwatcher.storage

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

object StorageManager {

    private val storageRef = FirebaseStorage.getInstance().reference

    fun upload(bitmap: Bitmap, onSuccessAction: (snapshot: UploadTask.TaskSnapshot) -> Unit,
               onFailureAction: () -> Unit) {
        val name = "Image_${System.currentTimeMillis()}.jpg"
        val ref = storageRef.child(name)

        val stream = ByteArrayOutputStream()
        val quality = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        val data = stream.toByteArray()

        ref.putBytes(data).addOnSuccessListener(onSuccessAction)
                .addOnFailureListener {
                    onFailureAction()
                }
    }

    fun upload(uri: Uri, onSuccessAction: (snapshot: UploadTask.TaskSnapshot) -> Unit,
               onFailureAction: () -> Unit) {
        val name = "Image_${System.currentTimeMillis()}.jpg"
        val ref = storageRef.child(name)

        ref.putFile(uri).addOnSuccessListener(onSuccessAction)
                .addOnFailureListener({
                    onFailureAction()
                })
    }

}