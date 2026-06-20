package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun IconInfoItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    textDecoration: TextDecoration = TextDecoration.None,
    color: Color = MaterialTheme.colorScheme.primary,
    alpha: Float = 1f
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = color
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier
                .alpha(alpha),
            text = text,
            fontSize = 10.sp,
            textDecoration = textDecoration,
            color = color,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
fun IconInfoItemPreview() {
    ReportsGoTheme {
        IconInfoItem(
            modifier = Modifier,
            ImageVector.vectorResource(R.drawable.ic_info),
            text = "Option"
        )
    }
}