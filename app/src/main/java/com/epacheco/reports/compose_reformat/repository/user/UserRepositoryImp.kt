package com.epacheco.reports.compose_reformat.repository.user

import android.net.Uri
import androidx.core.net.toUri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) :
    UserRepository {
    override suspend fun uploadProfileImage(imageUri: Uri): Resource<Uri> {
        return try {
            val sendUrlImg = getStorageReference()?.putFile(imageUri)?.await()
            val downloadUrlImg = sendUrlImg?.storage?.downloadUrl?.await()
            Resource.Success(downloadUrlImg.toString().toUri())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun getStorageReference(): StorageReference? {
        return firebaseAuth.uid?.let { userId ->
            firebaseStorage.getReference().child(Constants.DATABASE_FIREBASE_NAME)
                .child(userId)
                .child(Constants.CLIENT_IMAGES_PROFILE_TABLE_FIREBASE).child("$userId.jpg")
        }
    }


}