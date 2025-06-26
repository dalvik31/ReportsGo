package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowBasicTextField(textLabel : String = "Ingresa texto") {
    val value = remember { mutableStateOf("") }

    OutlinedTextField(
        value = value.value,
        onValueChange = { newValue ->
            value.value = newValue
        },
        label = {
            Text(
                text = textLabel,
                fontSize = 16.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(10.dp)
    )
}