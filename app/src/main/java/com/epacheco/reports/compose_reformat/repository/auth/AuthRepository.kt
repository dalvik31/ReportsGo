package com.epacheco.reports.compose_reformat.repository.auth

import android.net.Uri
import androidx.credentials.GetCredentialResponse
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): Resource<FirebaseUser>
    suspend fun loginGoogle(googleInfoAccount: GetCredentialResponse): Resource<FirebaseUser>
    suspend fun signIn(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    suspend fun recoveryPassword(email: String): Resource<Any>
    suspend fun updateImgProfile(uriImg: Uri?): Resource<Any>
    suspend fun sendEmailVerification(): Resource<Any>
    fun logout(): Resource<Any>
}