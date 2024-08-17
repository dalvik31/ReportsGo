package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.register.RegisterViewModel


@Composable
fun RecoveryPassword(registerViewModel: RegisterViewModel, navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        TextDivider(textDivider = "RecoveryPasswordScreen")
    }
}