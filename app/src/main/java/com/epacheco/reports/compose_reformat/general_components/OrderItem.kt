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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE5
import com.epacheco.reports.compose_reformat.utils.Utils.getCardBackground


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderItem(
    order: Order,
    onOrderClick: ((Order) -> Unit)? = null,
    onEditOrderClick: ((Order) -> Unit)? = null,
    onOrderLongClick: ((Order) -> Unit)? = null,
    isModeSelected: Boolean = false,
    isSelectedItem: Boolean = false,
    onOrderCheckedClick: ((Order) -> Unit)? = null,
    onOrderLocationClick: ((latitude: Double, longitude: Double) -> Unit)? = null,
    showEditBtn: Boolean = true
) {

    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
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
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Color.Transparent)
                    .wrapContentHeight()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

            ) {

                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(
                            getCardBackground(order.orderSeason)
                        )
                )
                Column(
                    Modifier.wrapContentHeight(),
                    Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
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
                            Box(modifier = Modifier.weight(1f, fill = false)) {
                                Text(
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Start,
                                    textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .alpha(if (order.orderBuy) 0.5f else 1f),
                                    text = order.orderName.replaceFirstChar { it.titlecase() },
                                    style = MaterialTheme.typography.titleSmall
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (showEditBtn) {
                                    Image(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                if (!isModeSelected) {
                                                    onEditOrderClick?.invoke(order)
                                                }
                                            },
                                        painter = painterResource(
                                            if (isModeSelected) {
                                                if (isSelectedItem) R.drawable.ic_check else R.drawable.ic_circle
                                            } else {
                                                R.drawable.ic_edit
                                            }
                                        ),
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
                                    .fillMaxWidth()
                                    .padding(start = 8.dp)
                                    .alpha(if (order.orderBuy) 0.5f else 1f),
                                text = order.orderDescription,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }


                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        order.orderClientName?.let { clientName ->
                            IconInfoItem(
                                icon = ImageVector.vectorResource(R.drawable.ic_vector_account),
                                text = clientName,
                                textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                alpha = if (order.orderBuy) 0.5f else 1f
                            )
                        }

                        IconInfoItem(
                            icon = ImageVector.vectorResource(R.drawable.ic_vector_activity),
                            text = DateUtils.dateFormat(order.orderId, FORMAT_DATE5),
                            textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                            alpha = if (order.orderBuy) 0.5f else 1f,
                        )
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))


                    val orderOptions = order.getOrderOptions()
                    if (orderOptions.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth()
                        ) {
                            orderOptions.forEach { tag ->
                                TagPillItem(
                                    tag = tag,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                    if (order.orderBuy) 0.5f else 1f
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    if (!order.address.isNullOrEmpty() && order.locationLong != null && order.locationLat != null) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd

                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.onBackground,
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                        )
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp),

                                ) {

                                IconInfoItem(
                                    modifier = Modifier.clickable {
                                        if (!isModeSelected) {
                                            onOrderLocationClick?.invoke(
                                                order.locationLat,
                                                order.locationLong
                                            )
                                        }
                                    },
                                    icon = ImageVector.vectorResource(R.drawable.ic_map_search),
                                    text = order.address,
                                    textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None,
                                    alpha = if (order.orderBuy) 0.5f else 1f,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )

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
    ReportsGoTheme {
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
                orderBuy = false,
                address = "adree"
            ), isModeSelected = false, showEditBtn = true
        )
    }

}
