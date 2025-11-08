package com.epacheco.reports.compose_reformat.repository.user

import android.net.Uri
import androidx.core.net.toUri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) :
    UserRepository {
    override suspend fun uploadProfileImage(imageFile: File): Resource<Uri> {
        return try {
            val uploadTask = getStorageReference()?.putFile(imageFile.toUri())?.await()
            val downloadUrlImg = uploadTask?.storage?.downloadUrl?.await()
            //val sendUrlImg = getStorageReference()?.putFile(imageUri)?.await()
            //val downloadUrlImg = sendUrlImg?.storage?.downloadUrl?.await()
            imageFile.delete()
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