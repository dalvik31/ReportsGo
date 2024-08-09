package com.epacheco.reports.compose_reformat.utils

import com.epacheco.reports.compose_reformat.utils.extensions.validateEmail
import com.epacheco.reports.compose_reformat.utils.extensions.validatePassword

object Validations {
    fun validateEmailAndPassword(email: String, password: String): Boolean =
        email.validateEmail() && password.validatePassword()

}