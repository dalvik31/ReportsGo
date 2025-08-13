package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun SearchBarElement(
    modifier: Modifier = Modifier,
    searchText: String,
    searchHintText: String,
    onTextChange: (String) -> Unit
) {
    val maxLength = 50

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = searchText,
        label = {
            Text(
                text = searchHintText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                style = androidx.compose.ui.text.TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            )
        },
        shape = RoundedCornerShape(10.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Light,
            background = Color.Transparent,
            fontSize = 16.sp
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences
        ),
        onValueChange = {
            if (it.length <= maxLength) {
                onTextChange(it)
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                val image = Icons.Filled.Close
                IconButton(onClick = {
                    onTextChange("")
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

    )
}

@Preview(showBackground = true)
@Composable
fun CustomSearchBarPreview() {
    ReportsGoTheme {
        SearchBarElement(
            searchText = "searchText",
            searchHintText = "searchText",
            onTextChange = {})
    }

}