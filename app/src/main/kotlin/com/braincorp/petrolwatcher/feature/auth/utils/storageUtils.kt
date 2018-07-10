package com.braincorp.petrolwatcher.feature.auth.utils

import android.graphics.Bitmap
import com.braincorp.petrolwatcher.utils.toByteArray
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

fun uploadBitmap(bitmap: Bitmap?,
                 onSuccessAction: (snapshot: UploadTask.TaskSnapshot) -> Unit,
                 onFailureAction: () -> Unit) {
    val rootReference = FirebaseStorage.getInstance().reference
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

    val name = "profile.jpg"
    val pictureReference = rootReference.child("users/$uid/$name")

    val data = bitmap?.toByteArray() ?: return

    pictureReference.putBytes(data)
            .addOnSuccessListener(onSuccessAction)
            .addOnFailureListener { onFailureAction() }
}