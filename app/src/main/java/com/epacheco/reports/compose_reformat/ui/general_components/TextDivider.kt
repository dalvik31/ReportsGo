package com.epacheco.reports.compose_reformat.ui.general_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextDivider(textDivider: String, primaryColor: Color = MaterialTheme.colorScheme.primary) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            Modifier.weight(1f),
            thickness = 1.dp,
            color = primaryColor
        )
        Text(
            text = textDivider,
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
        )

        HorizontalDivider(
            Modifier.weight(1f),
            thickness = 1.dp,
            color = primaryColor
        )
    }
}

@Preview
@Composable
private fun ShowTextDivider() {
    TextDivider(textDivider = "OR")
}