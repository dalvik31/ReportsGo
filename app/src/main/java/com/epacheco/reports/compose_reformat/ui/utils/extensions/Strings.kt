package com.epacheco.reports.compose_reformat.ui.utils.extensions

import android.util.Patterns


fun String.validateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.validatePassword(): Boolean = this.length > 6