package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.GrayLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    inputText: String,
    hintText: String,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLength: Int = 50,
    onTextChange: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = inputText,
        textStyle = TextStyle.Default.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        ),
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = {
            Text(
                text = hintText,
                color = GrayLight,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onValueChange = {
            if (it.length <= maxLength) onTextChange(it)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = tintColor,
            disabledIndicatorColor = tintColor,
            focusedIndicatorColor = tintColor,
            cursorColor = tintColor
        ), enabled = enabled

    )
}

@Preview
@Composable
private fun InputTextFieldPreview() {
    ReportsGoTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            InputTextField(
                inputText = "",
                hintText = "lalla",
                tintColor = MaterialTheme.colorScheme.primary
            ) {}
        }
    }
}

