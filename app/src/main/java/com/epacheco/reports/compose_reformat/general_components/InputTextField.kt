package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    textHint: String = stringResource(id = R.string.lbl_empty),
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enable: Boolean = true,
    onTextChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            onTextChange.invoke(newValue)
        },
        label = {
            Text(
                text = textHint,
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
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = if (singleLine) IntrinsicSize.Min else IntrinsicSize.Max),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else 5,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        enabled = enable,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun ShowEmailTextField() {
    ReportsGoTheme {
        InputTextField(textValue = "", textHint = "BasicTextField", enable = false)
    }
}
