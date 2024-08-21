package com.epacheco.reports.compose_reformat.repository.auth

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    fun logout()
}