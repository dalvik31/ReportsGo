package com.epacheco.reports.compose_reformat.repository.user

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) :
    UserRepository {
    override suspend fun uploadProfileImage(imageUri: Uri): Resource<String> {
        var exceptionUploadPicture: Exception? = null
        var downloadUrl: String? = null
        return try {
            getStorageReference()?.let {
                // Upload file to Firebase Storage and await completion
                it.putFile(imageUri)
                // Fetch the download URL
                downloadUrl = getStorageReference()!!.downloadUrl.await().toString()
            } ?: run {
                exceptionUploadPicture = Exception("Usuario no encontrado")
            }
            exceptionUploadPicture?.let {
                Resource.Failure(it)
            } ?: run {
                downloadUrl?.let { url ->
                    Resource.Success(url)
                } ?: run {
                    Resource.Failure(Exception("Error al subir imagen"))
                }

            }
            /* getStorageReference()?.let {
                 // Upload file to Firebase Storage and await completion
                 it.putFile(imageUri)
                 // Fetch the download URL
                 downloadUrl = it.downloadUrl.await().result
             } ?: run {
                 exceptionUploadPicture = Exception("Usuario no encontrado")
             }

             exceptionUploadPicture?.let {
                 Resource.Failure(it)
             } ?: run {
                 downloadUrl?.let { url ->
                     Resource.Success(url)
                 } ?: run {
                     Resource.Failure(Exception("Error al subir imagen"))
                 }

             }*/

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
        /*try {
            // Reference to where the image will be saved in Firebase Storage
            val storageRef = storage.reference.child("profile_pictures/${imageUri.lastPathSegment}")

            // Upload file to Firebase Storage and await completion
            storageRef.putFile(imageUri).await()

            // Fetch the download URL
            val downloadUrl = storageRef.downloadUrl.await().toString()
            emit(Result.success(downloadUrl))

        } catch (e: Exception) {
            // Log the error message if the upload fails
            Log.e("UserRepositoryImpl", "Error uploading image: ${e.message}", e)
            emit(Result.failure(e))
        }*/
    }

    override fun getStorageReference(): StorageReference? {
        return firebaseAuth.uid?.let { userId ->
            firebaseStorage.getReference().child(Constants.DATABASE_FIREBASE_NAME)
                .child(userId)
                .child(Constants.CLIENT_IMAGES_PROFILE_TABLE_FIREBASE).child("$userId.jpg")
        }
    }


}