package com.epacheco.reports.compose_reformat.ui.utils

import com.epacheco.reports.compose_reformat.ui.utils.extensions.validateEmail
import com.epacheco.reports.compose_reformat.ui.utils.extensions.validatePassword

object Validations {
    fun validateEmailAndPassword(email: String, password: String): Boolean =
        email.validateEmail() && password.validatePassword()

}