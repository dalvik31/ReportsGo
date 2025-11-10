package com.epacheco.reports.compose_reformat.utils.extensions

import android.content.Context
import android.util.Patterns
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.epacheco.reports.R


fun String.validateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.validatePassword(): Boolean = this.length >= 6

fun String.getNameFromEmail(): String = this.substringBefore("@")

fun String?.nameProfile(email: String?): String {
    return if (this.isNullOrEmpty()) {
        if (email.isNullOrEmpty()) "" else email.getNameFromEmail()
    } else checkNotNull(this)
}

fun String.toColor(): Color {
    return Color(this.toColorInt())
}


fun String.getTranslateFireBaseErrorMsg(ctx: Context): String =
    ctx.getString(
        when {
            this.contains("There is no user record corresponding to this identifier") -> R.string.msg_firebase_user_not_found
            this.contains("The password is invalid or the user does not have a password") -> R.string.msg_firebase_password_invalid
            this.contains("The email address is already in use by another account") -> R.string.msg_firebase_email_exist_already
            else -> R.string.general_error
        }
    )


fun String.getNameProductImage(): String =
    this.substringAfter("Images%2F")
        .substringBefore("?alt=media")
        .trim()



fun String.initials(): String {
    val words = split(" ", "-") // Split by space and hyphen
    val initials = StringBuilder()
    for (word in words) {
        if (word.isNotEmpty()) {
            initials.append(word.first().uppercaseChar())
        }
    }
    return initials.toString()
}


fun String.fromPath(): String {
    val name = if (this.contains("/")) {
        this.split("/").first()
    } else if (this.contains("?")) {
        this.split("?").first()
    } else this
    return name
}

fun String.validateContent(): String?{
    return this.ifEmpty { null }
}
