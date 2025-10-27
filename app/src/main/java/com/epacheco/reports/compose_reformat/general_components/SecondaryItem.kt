package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White

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
    customHeight: Dp? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
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
        Column() {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .size(16.dp),
                    imageVector = ImageVector.vectorResource(
                        icon ?: R.drawable.baseline_circle_24
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if (icon != null) tintIcon else Color.Transparent)
                )

                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 8.dp),
                    text = text ?: "",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (strikeThrough) FontWeight.Light else FontWeight.Bold,
                        textDecoration = if (strikeThrough) TextDecoration.LineThrough else TextDecoration.None,
                    ),

                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                contentText ?: "",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            Row {
                Spacer(modifier = modifier.weight(1f))
                Text(
                    secondaryText ?: "",
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

    /*    Card(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .alpha(if (strikeThrough) 0.5f else 1f)
                .clickable {
                    onItemPressed?.invoke()
                },
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )

        ) {


            Column(
                horizontalAlignment = Alignment.End,
            ) {

                Row(
                    modifier = modifier.padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {


                    Image(
                        modifier = Modifier
                            .size(16.dp),
                        imageVector = ImageVector.vectorResource(
                            icon ?: R.drawable.baseline_circle_24
                        ),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (icon != null) tintIcon else Color.Transparent)
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp),
                        text = text ?: "",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (strikeThrough) FontWeight.Light else FontWeight.Bold,
                            textDecoration = if (strikeThrough) TextDecoration.LineThrough else TextDecoration.None
                        ), fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = modifier.weight(1f))

                }



                Text(
                    modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
                    text = secondaryText ?: "",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,

                    )

                Box(
                    modifier = modifier
                        .background(
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(topStart = 10.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),

                    ) {
                    Text(
                        text = secondaryText ?: "",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,

                        )
                }

            }

        }
    */
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SecondaryItemPreview() {
    ReportsGoTheme {
        Row {
            SecondaryItem(
                modifier = Modifier.weight(1f),
                text = "text",
                contentText = "contentText",
                secondaryText = "secondaryText",
                icon = R.drawable.baseline_circle_24,
                strikeThrough = false,
                customHeight = 70.dp
            )
            SecondaryItem(
                modifier = Modifier.weight(1f),
                text = "text",
                secondaryText = "secondaryText",
                icon = R.drawable.baseline_circle_24,
                strikeThrough = false,
                customHeight = 70.dp
            )
        }
    }
}