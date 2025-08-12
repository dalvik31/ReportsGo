package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.RedLight
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PasswordTextField(
    password: String,
    passwordHint: String = stringResource(id = R.string.lbl_empty),
    onTextChange: (String) -> Unit
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    val maxLength = 50
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = { newValue ->

            if (newValue.length <= maxLength) onTextChange.invoke(newValue)
        },
        label = {
            Text(
                text = passwordHint,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                style = androidx.compose.ui.text.TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Light,
            background = Color.Transparent,
            fontSize = 16.sp
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = RedLight,
            focusedIndicatorColor = RedLight,
            disabledIndicatorColor = RedLight,
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    imageVector = image,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

    )
}

@Preview
@Composable
private fun ShowEmailTextField() {
    ReportsGoTheme {
        PasswordTextField("") {}
    }

}

