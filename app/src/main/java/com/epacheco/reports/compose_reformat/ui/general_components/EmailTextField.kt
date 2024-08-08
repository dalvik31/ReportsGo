package com.epacheco.reports.compose_reformat.ui.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.compose_reformat.ui.theme.RedLight
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun EmailTextField(email: String, onTextChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.primary) },
        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
        textStyle = TextStyle(),
        singleLine = true,
        trailingIcon = {
            Icon(Icons.Filled.Email, "", tint = MaterialTheme.colorScheme.primary)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFFB2B2B2),
            unfocusedContainerColor = RedLight,
            focusedContainerColor = RedLight,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
}

@Composable
fun otherEmail(){
    var textState by remember { mutableStateOf("") }
    val maxLength = 110
    val lightBlue = Color(0xffd8e6ff)
    val blue = Color(0xff76a9ff)
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = textState,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = RedLight,
            disabledLabelColor = RedLight,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = "Email", color = MaterialTheme.colorScheme.primary) },
        onValueChange = {
            if (it.length <= maxLength) textState = it
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        trailingIcon = {
            if (textState.isNotEmpty()) {
                IconButton(onClick = { textState = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
private fun ShowEmailTextField() {
    EmailTextField("") {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShowOtherEmailTextField() {
    otherEmail()
}