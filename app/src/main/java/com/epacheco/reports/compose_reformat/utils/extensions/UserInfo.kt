package com.epacheco.reports.compose_reformat.utils.extensions

import android.util.Log
import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile
import com.epacheco.reports.compose_reformat.utils.DateUtils
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import kotlin.toString

fun UserInfo.toDomain(): UserInfoProfile {
    Log.d("UserInfo", "UserInfo : toDomain: $this")
    return UserInfoProfile(
        id = this.id,
        email = this.email.orEmpty(),
        fullName =
            this.userMetadata
                ?.get("full_name")
                ?.jsonPrimitive
                ?.content
                .getName(this.email),
        avatarUrl =
            this.userMetadata
                ?.get("avatar_url")
                ?.jsonPrimitive
                ?.content,
        createdAt = DateUtils.formatDateIsoToText(this.emailConfirmedAt.toString(), DateUtils.FORMAT_DATE6),
        lastSignInAt = DateUtils.formatDateIsoToText(this.lastSignInAt.toString(), DateUtils.FORMAT_DATE9),
        signInMethod = this.identities?.lastOrNull()?.provider,
        isEmailVerified =
            this.identities
                ?.lastOrNull()
                ?.identityData
                ?.get("email_verified")
                ?.jsonPrimitive
                ?.content
                ?.toBoolean() ?: false,
    )
}
