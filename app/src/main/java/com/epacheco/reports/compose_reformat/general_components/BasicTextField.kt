package com.epacheco.reports.compose_reformat.general_components

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShowBasicTextField(textLabel : String = "Ingresa texto", keyboard: KeyboardType = KeyboardType.Text, textValue : (String) -> Unit = {}) {
    val value = remember { mutableStateOf("") }

    OutlinedTextField(
        value = value.value,
        onValueChange = { newValue ->
            value.value = newValue
            textValue.invoke(value.value)
        },
        label = {
            Text(
                text = textLabel,
                fontSize = 16.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboard)
    )
}