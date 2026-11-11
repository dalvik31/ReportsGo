package com.epacheco.reports.compose_reformat.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoProfile(
    val id: String,
    val email: String? = null,
    val fullName: String? = null,
    val avatarUrl: String? = null,
    val createdAt: String? = null,
    val lastSignInAt: String? = null,
    val signInMethod: String? = null,
    val isEmailVerified: Boolean = false,
)
