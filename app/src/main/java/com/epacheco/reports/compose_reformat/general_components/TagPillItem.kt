package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun TagPillItem(
    text: String,
    backgroundColor: Color,
    textDecoration: TextDecoration = TextDecoration.None,
    alpha: Float = 1f
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier
                .alpha(alpha),
            text = text,
            textDecoration = textDecoration,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,)
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun TagPillItemPreview() {
    ReportsGoTheme {
        TagPillItem(
            text = "TagPillItemPreview",
            backgroundColor = MaterialTheme.colorScheme.primary
        )
    }
}