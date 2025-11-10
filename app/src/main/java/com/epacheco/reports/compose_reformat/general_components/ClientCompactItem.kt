package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClientCompactItem(
    modifier: Modifier = Modifier,
    text: String? = null,
    contentText: String? = null,
    secondaryText: String? = null,
    avatarUrl: String? = null,
    avatarLetters: String? = null,
    progressLimit: Float = 0f,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .wrapContentHeight(),
        colors = CardColors(
            contentColor = White,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {

        Box {
            secondaryText?.let {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        secondaryText,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(bottomStart = 10.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }

            Column(
                modifier = Modifier.combinedClickable(
                    onClick = {
                        onClick?.invoke()
                    },
                    onLongClick = { onLongClick?.invoke() },
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    avatarUrl?.let {
                        AvatarWithIndicator(
                            avatarUrl = it,
                            indicatorRes = R.drawable.ic_simple_dot
                        )
                    }

                    avatarLetters?.let {
                        AvatarWithIndicator(
                            avatarLetters = it,
                            indicatorRes = R.drawable.ic_simple_dot,
                            indicatorSize = 15.dp,
                            avatarSize = 60.dp,
                            tintSaleIndicator = Utils.getClientDotBackground(
                                progressLimit
                            )
                        )
                    }

                    Column(

                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentWidth(),
                            text = text ?: "",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.None,
                            ),

                            color = MaterialTheme.colorScheme.primary
                        )
                        contentText?.let {
                            Text(
                                contentText,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                }


            }


        }

    }
}

@Preview
@Composable
fun ClientCompactItemPreview() {
    ReportsGoTheme {
        Row {
            ClientCompactItem(
                modifier = Modifier,
                avatarUrl = "",
                text = "text",
                contentText = "contentText",
                secondaryText = "secondaryText",
                progressLimit = 0.4f
            )

        }
    }
}