package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsOptionDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE4
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE5
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE7
import com.epacheco.reports.compose_reformat.utils.Utils
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderItem(
    order: Order,
    onOrderClick: ((Order) -> Unit)? = null,
    onOrderLongClick: ((Order) -> Unit)? = null,
    isModeSelected: Boolean = false,
    isSelectedItem: Boolean = false,
    onOrderCheckedClick: ((Order) -> Unit)? = null,
) {

    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .alpha(if (isSelectedItem) 0.5f else 1f)
                .wrapContentHeight()
                .combinedClickable(
                    onClick = {
                        if (isModeSelected) {
                            onOrderCheckedClick?.invoke(order)
                        } else {
                            onOrderClick?.invoke(order)
                        }

                    },
                    onLongClick = {
                        onOrderLongClick?.invoke(order)
                    },
                ),
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {


            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

            ) {
                Column(
                    Modifier.wrapContentHeight(),
                    Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box() {
                                Image(
                                    modifier = Modifier
                                        .size(16.dp),
                                    painter = when (order.orderSeason) {
                                        Season.FALL -> painterResource(R.drawable.ic_snow)
                                        Season.SPRING -> painterResource(R.drawable.ic_sun)
                                        else -> painterResource(R.drawable.ic_simple_dot)
                                    },
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                )

                            }
                            Box(modifier = Modifier.weight(1f, fill = false)) {
                                Text(
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Start,
                                    textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                        .alpha(if (order.orderBuy) 0.5f else 1f),
                                    text = order.orderName.replaceFirstChar { it.titlecase() },
                                    style = MaterialTheme.typography.titleSmall
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .size(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isModeSelected) {
                                    Image(
                                        modifier = Modifier
                                            .size(24.dp),
                                        painter = painterResource(if (isSelectedItem) R.drawable.ic_check else R.drawable.ic_circle),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                    )
                                }

                            }
                        }


                    }

                    Row {
                        if (order.orderDescription.isNotEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.description),
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                text = order.orderDescription,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Row {
                        if (order.orderSize.isNotEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.size),
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth()
                                    .alpha(if (order.orderBuy) 0.5f else 1f)
                                    .padding(start = 8.dp),
                                text = order.orderSize,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Row {
                        if (order.orderColor.isNotEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.color),
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                text = order.orderColor,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }


                    Row {
                        if (order.orderGender.isNotEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.gender),
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                text = order.orderGender,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Row {
                        if (!order.orderClientName.isNullOrEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                fontWeight = FontWeight.Bold,
                                text = stringResource(R.string.order_client),
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                text = order.orderClientName,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,

                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Box() {
                                if (!order.address.isNullOrEmpty()) {
                                    Image(
                                        modifier = Modifier
                                            .size(24.dp),
                                        painter = painterResource(R.drawable.ic_map_search),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                                    )
                                }


                            }
                            Box(modifier = Modifier.weight(1f, fill = false)) {
                                if (!order.address.isNullOrEmpty()) {
                                    Text(
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp)
                                            .alpha(if (order.orderBuy) 0.5f else 1f),
                                        text = order.address,
                                        style = MaterialTheme.typography.titleSmall,
                                        textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier.wrapContentWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.onBackground,
                                            RoundedCornerShape(
                                                topStart = 10.dp,
                                                bottomStart = 10.dp
                                            )
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),

                                    ) {

                                    Text(
                                        modifier = Modifier
                                            .alpha(if (order.orderBuy) 0.8f else 1f)
                                            .alpha(if (order.orderBuy) 0.5f else 1f),
                                        text = DateUtils.dateFormat(order.orderId, FORMAT_DATE5),
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall,
                                        textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
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


@Preview
@Composable
fun OrderItemPreview() {
    OrderItem(
        order = Order(
            orderId = "0",
            orderName = "name",
            orderSeason = Season.SPRING,
            orderDescription = "descripcion",
            orderSize = "Grande",
            orderColor = "Color",
            orderGender = "Genero",
            orderClientName = "Cliente",
            orderBuy = true
        ),
    )
}
