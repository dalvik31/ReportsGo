package com.epacheco.reports.compose_reformat.repository.user

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

interface UserRepository {
    suspend fun uploadProfileImage(imageUri: Uri): Resource<String>
    fun getStorageReference(): StorageReference?
}