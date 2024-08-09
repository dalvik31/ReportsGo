package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GreyLight
import com.epacheco.reports.compose_reformat.ui.theme.RedDark


@Composable
fun EmailTextField(email: String, onTextChange: (String) -> Unit) {
    val maxLength = 50
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = email,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = {
            Text(
                text = stringResource(id = R.string.register_screen_hint_email),
                color = MaterialTheme.colorScheme.onTertiary,
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
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        )

    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShowEmailTextField() {
    EmailTextField("") {}
}

