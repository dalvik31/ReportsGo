package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.GrayLight
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.ui.theme.White60
import com.epacheco.reports.compose_reformat.ui.theme.FallColor
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE3


@Composable
fun OrderItem(
    order: Order,
    onMainOrderClick: (Order) -> Unit,
    onDeleteOrderClick: (String) -> Unit,
    onUpdateStatusOrderClick: (Order) -> Unit

) {

    var showDialogConfirmDeleteOrder by remember { mutableStateOf(false) }
    var showDialogConfirmCompleteOrder by remember { mutableStateOf(false) }

    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .alpha(if (order.orderStatus == OrderStatus.IN_PROGRESS) 1f else .8f)
                .fillMaxWidth()
                .height(100.dp)
                .clickable {
                    onMainOrderClick.invoke(order)
                },
            colors = CardColors(
                contentColor = White,
                containerColor = getCardBackground(order),
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    modifier = Modifier
                        .width(120.dp)
                        .alpha(.2f)
                        .align(Alignment.CenterEnd)
                        .graphicsLayer {
                            rotationZ = 20f
                        },
                    painter = painterResource(if (order.orderStatus == OrderStatus.IN_PROGRESS) R.drawable.ic_vector_products else R.drawable.ic_vector_checked),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(White60),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )

                ) {
                    Column(Modifier.fillMaxSize(), Arrangement.SpaceBetween) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = order.nameOrder.uppercase(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ), fontSize = 14.sp
                            )

                            IconButton({
                                showDialogConfirmCompleteOrder = true
                            }) {
                                Icon(
                                    painter = painterResource(if (order.orderStatus == OrderStatus.IN_PROGRESS) R.drawable.ic_vector_unchecked else R.drawable.ic_vector_checked),
                                    contentDescription = null,
                                    tint = White
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            IconButton({
                                showDialogConfirmDeleteOrder = true
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_vector_remove),
                                    contentDescription = null,
                                    tint = White
                                )
                            }
                            if (order.orderId.isNotEmpty()) {
                                Text(
                                    text = DateUtils.format(
                                        order.orderId.toLong(),
                                        FORMAT_DATE3
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }


                        }

                    }

                }
            }
        }

    }


    if (showDialogConfirmDeleteOrder) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_order_title),
            dialogSubTitle = stringResource(
                R.string.msg_delete_order_list_body,
                order.nameOrder
            ),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteOrder = false },
            onConfirmation = {
                showDialogConfirmDeleteOrder = false
                onDeleteOrderClick.invoke(order.orderId)
            }
        )
    }

    if (showDialogConfirmCompleteOrder) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.msg_update_state_order_title),
            dialogSubTitle = getUpdateStatusList(order),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmCompleteOrder = false },
            onConfirmation = {
                showDialogConfirmCompleteOrder = false
                onUpdateStatusOrderClick.invoke(order)
            }
        )
    }

}


private fun getCardBackground(orderMain: Order): Color =
    when (orderMain.orderSeason) {
        Season.FALL -> FallColor
        Season.SPRING -> com.epacheco.reports.compose_reformat.ui.theme.SpringColor
        null -> GrayLight
    }


@Composable
private fun getUpdateStatusList(order: Order) =
    stringResource(
        when (order.orderStatus) {
            OrderStatus.IN_PROGRESS -> R.string.msg_complete_order_body
            OrderStatus.DONE -> R.string.msg_in_progress_order_body
        }, order.nameOrder
    )


@Preview
@Composable
fun OrderMainItemPreview() {
    OrderItem(
        Order(orderSeason = Season.FALL, nameOrder = "name"),
        onMainOrderClick = {},
        onDeleteOrderClick = {}, onUpdateStatusOrderClick = {})
}
