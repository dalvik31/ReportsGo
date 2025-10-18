package com.epacheco.reports.compose_reformat.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.FallColor
import com.epacheco.reports.compose_reformat.ui.theme.SpringColor
import com.epacheco.reports.compose_reformat.utils.extensions.validateEmail
import com.epacheco.reports.compose_reformat.utils.extensions.validatePassword

object Validations {
    fun validateEmailAndPassword(email: String, password: String): Boolean =
        email.validateEmail() && password.validatePassword()

}