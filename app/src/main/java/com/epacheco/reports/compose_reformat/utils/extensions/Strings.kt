package com.epacheco.reports.compose_reformat.utils.extensions

import android.util.Patterns
import com.epacheco.reports.R


fun String.validateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.validatePassword(): Boolean = this.length >= 6

fun String.getNameFromEmail(): String = this.substringBefore("@")

fun String.getTranslateFireBaseErrorMsg(): Int? =
    when {
        this.contains("There is no user record corresponding to this identifier") -> R.string.msg_firebase_user_not_found
        this.contains("The password is invalid or the user does not have a password") -> R.string.msg_firebase_password_invalid
        else -> null
    }

