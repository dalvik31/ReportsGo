package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SecondaryItem(
    modifier: Modifier = Modifier,
    text: String? = null,
    contentText: String? = null,
    secondaryText: String? = null,
    icon: Int? = null,
    tintIcon: Color = MaterialTheme.colorScheme.primary,
    strikeThrough: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (strikeThrough) 0.5f else 1f)
            .combinedClickable(
                onClick = {
                    onClick?.invoke()
                },
                onLongClick = { onLongClick?.invoke() },
            ),
        colors = CardColors(
            contentColor = White,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Column(
            Modifier
                .wrapContentHeight()
                .padding(start = 8.dp, top = 8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon?.let {
                    Image(
                        modifier = Modifier
                            .size(16.dp)
                            .padding(),
                        imageVector = ImageVector.vectorResource(
                            icon ?: R.drawable.ic_simple_dot
                        ),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (icon != null) tintIcon else Color.Transparent)
                    )


                }

                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = if (icon != null) 8.dp else 0.dp),
                    text = text?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                        ?: "",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (strikeThrough) FontWeight.Light else FontWeight.Bold,
                        textDecoration = if (strikeThrough) TextDecoration.LineThrough else TextDecoration.None,
                    ),

                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                AnnotatedString.fromHtml(contentText ?: ""),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 5.dp, bottom = 5.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Light,
                    textDecoration = if (strikeThrough) TextDecoration.LineThrough else TextDecoration.None,
                ),
            )
            secondaryText?.let {
                Row {
                    Spacer(modifier = modifier.weight(1f))

                    Text(
                        secondaryText,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(topStart = 10.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }


        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SecondaryItemPreview() {
    ReportsGoTheme {
        SecondaryItem(
            modifier = Modifier,
            text = "text",
            contentText = "Context text",
            secondaryText = "Secondary text",
            icon = R.drawable.ic_simple_dot,
            strikeThrough = false,
        )
    }
}