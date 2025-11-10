package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientItem(
    modifier: Modifier = Modifier,
    client: Client? = null,
    showFullName: Boolean = false,
    iconAction: Int? = null,
    actionText: String? = null,
    onClick: (() -> Unit)? = null,
    onClickIcon: (() -> Unit)? = null
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick?.invoke()
            },
        colors = CardColors(
            contentColor = White,
            containerColor = client?.let { MaterialTheme.colorScheme.surface }
                ?: run {
                    Color.Transparent
                },
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {

            actionText?.let {
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(bottomStart = 10.dp)
                        )
                        .clickable {
                            onClick?.invoke()
                        }
                        .padding(horizontal = 10.dp, vertical = 5.dp),


                    ) {


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = actionText,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall,
                        )

                        iconAction?.let {
                            Icon(
                                painter = painterResource(iconAction),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(start = 5.dp)
                                    .clickable {
                                        onClickIcon?.invoke()
                                    },
                                tint = MaterialTheme.colorScheme.onPrimary,

                                )
                        }


                    }

                }


            }


            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                        .padding(top = 12.dp)
                ) {
                    client?.let {
                        Icon(
                            painter = painterResource(R.drawable.ic_simple_dot),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = client?.let {
                                Utils.getClientDotBackground(
                                    client.geProgressLimit()
                                )
                            } ?: run { Color.Transparent }

                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth(),
                                text = if (showFullName) client.name.plus(" ")
                                    .plus(client.lastNanme) else client.name,
                                style = MaterialTheme.typography.titleSmall
                            )

                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth(),
                                text = " • $${client.debt}",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))


                }

                client?.let {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        TextDivider(
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            text = stringResource(
                                R.string.credit_client,
                                it.getLimitAvailable()
                            )
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(vertical = 12.dp)
                                .padding(end = 12.dp, start = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Column() {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth(),
                                    gapSize = (1).dp,
                                    progress = {
                                        client?.geProgressLimit() ?: 0f
                                    },
                                    drawStopIndicator = {
                                        drawStopIndicator(
                                            drawScope = this,
                                            stopSize = ProgressIndicatorDefaults.LinearTrackStopIndicatorSize,
                                            color = Color.Transparent,
                                            strokeCap = StrokeCap.Round,
                                        )
                                    }

                                )
                                Row() {
                                    Text(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(top = 10.dp),
                                        text = "$${client.debt}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.End
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(top = 10.dp),
                                        text = "$${client.limit}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.End
                                    )
                                }


                            }


                        }

                    }

                }


            }

        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ClientItemPreview() {
    ReportsGoTheme {
        Column {
            ClientItem(
                client = Client(name = "Cliente", phone = "5548562659"),
                iconAction = R.drawable.ic_vector_phone, actionText = "5548562659"
            )
            ClientItem(iconAction = R.drawable.ic_error, actionText = "5548562659")
            ClientItem(
                client = Client(name = "Cliente", phone = "5548562659"),
                iconAction = R.drawable.ic_error,
                actionText = "5548562659",
            )
        }

    }
}
