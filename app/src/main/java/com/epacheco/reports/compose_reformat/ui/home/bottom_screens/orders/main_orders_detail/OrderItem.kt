package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.FallColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE3
import com.epacheco.reports.compose_reformat.utils.extensions.getNameSeason


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderItem(
    order: Order,
    onMainOrderClick: (Order) -> Unit,
    onDeleteOrderClick: (String) -> Unit,
    onUpdateStatusOrderClick: (Order) -> Unit

) {

    var showDialogOptionsOrder by remember { mutableStateOf(false) }
    var showDialogConfirmDeleteOrder by remember { mutableStateOf(false) }
    var showDialogConfirmCompleteOrder by remember { mutableStateOf(false) }

    /* Surface(color = Color.Transparent) {
         Box(contentAlignment = Alignment.Center) {
             Image(
                 modifier = Modifier
                     .width(200.dp)
                     .alpha(.2f)
                     .align(Alignment.CenterEnd)
                     .graphicsLayer {
                         rotationZ = 20f
                     },

                 painter = painterResource(if (order.orderBuy) R.drawable.ic_vector_order else R.drawable.ic_vector_checked),
                 contentDescription = null,
                 colorFilter = ColorFilter.tint(White60),
                 contentScale = ContentScale.Crop
             )*/
    /*Image(
        modifier = Modifier
            .width(200.dp)
            .alpha(.2f)
            .align(Alignment.CenterEnd)
            .graphicsLayer {
                rotationZ = 220f
            },
        painter = painterResource(if (order.orderBuy) R.drawable.ic_vector_order else R.drawable.ic_vector_checked),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )*/

    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .alpha(if (order.orderBuy) 0.5f else 1f)
                .fillMaxWidth()
                .height(100.dp)
                .combinedClickable(
                    onClick = { //showDialogConfirmCompleteOrder = true
                        onUpdateStatusOrderClick.invoke(order)
                    },
                    onLongClick = { showDialogOptionsOrder = true },
                ),
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )

        ) {

            /* Box(modifier = Modifier.fillMaxSize()) {
             Card(
                 modifier = Modifier
                     .padding(vertical = 4.dp, horizontal = 8.dp)
                     .alpha(if (order.orderBuy) 0.5f else 1f)
                     .fillMaxWidth()
                     .height(intrinsicSize = IntrinsicSize.Min)
                     .clickable {
                         showDialogConfirmCompleteOrder = true
                         //onMainOrderClick.invoke(order)
                     }
             ) {*/


            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (order.orderId.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(topStart = 10.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),

                        ) {
                        Text(
                            text = DateUtils.format(order.orderId.toLong(), FORMAT_DATE2),
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodySmall,

                        )
                    }
                }

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


                    Column() {

                        /* Text(
                             text = stringResource(
                                 order.orderSeason?.name?.getNameSeason() ?: R.string.lbl_empty
                             ),
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(20.dp)
                                 .background(MaterialTheme.colorScheme.primary)
                                 .wrapContentHeight(align = Alignment.CenterVertically),
                             color = MaterialTheme.colorScheme.onPrimary,
                             textAlign = TextAlign.Center,
                             style = MaterialTheme.typography.bodySmall.copy(
                                 fontWeight = FontWeight.Light
                             ),
                             fontSize = 14.sp,
                         )*/


                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            /* IconButton (
                                 modifier = Modifier
                                     .clip(CircleShape)
                                     .padding(10.dp),
                                 painter = painterResource(R.drawable.ic_vector_products),
                                 contentDescription = null,
                                 colorFilter = ColorFilter.tint(getCardBackground(order)),
                                 contentScale = ContentScale.Crop
                             )*/

                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_circle_24),
                                    contentDescription = null,
                                    tint = getCardBackground(order),
                                    modifier = Modifier.size(16.dp)
                                )
                            }


                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = order.orderName.uppercase(),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = if (order.orderBuy) FontWeight.Light else FontWeight.Bold,
                                    textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None
                                ), fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            /* IconButton(onClick = {
                                 onMainOrderClick.invoke(order)
                             }) {
                                 Icon(
                                     painter = painterResource(R.drawable.ic_vector_modify),
                                     contentDescription = null,
                                     tint = getCardBackground(order)
                                 )
                             }*/

                            /*IconButton(modifier = Modifier.weight(.12f), onClick = {
                                showDialogConfirmCompleteOrder = true
                            }) {
                                Icon(
                                    painter = painterResource(if (order.orderBuy) R.drawable.ic_vector_checked else R.drawable.ic_vector_unchecked),
                                    contentDescription = null,
                                )
                            }*/
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /*IconButton(onClick = {
                                showDialogConfirmDeleteOrder = true
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_vector_remove),
                                    contentDescription = null,
                                    tint =  getCardBackground(order)
                                )
                            }*/
                            Spacer(modifier = Modifier.weight(1f))
                            /*if (order.orderId.isNotEmpty()) {
                                Text(
                                    modifier = Modifier.padding(end = 12.dp),
                                    text = DateUtils.format(
                                        order.orderId.toLong(),
                                        FORMAT_DATE2
                                    ),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        textDecoration = if (order.orderBuy) TextDecoration.LineThrough else TextDecoration.None
                                    ),
                                    fontWeight = if (order.orderBuy) FontWeight.Light else FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }*/


                        }

                    }

                }

            }

            // }

        }


    }

    if (showDialogOptionsOrder) {
        ReportsOptionDialog(
            imgDialog = R.drawable.ic_vector_order,
            dialogTitle = stringResource(R.string.msg_dialog_options_title),
            dialogSubTitle = stringResource(R.string.msg_dialog_options_body),
            firstOptionText = stringResource(R.string.msg_dialog_edit_order),
            secondOptionText = stringResource(R.string.msg_dialog_delete_order),
            onDismissRequest = { showDialogOptionsOrder = false },
            onFirstConfirmation = {
                showDialogOptionsOrder = false
                onMainOrderClick.invoke(order)

            },
            onSecondConfirmation = {
                showDialogOptionsOrder = false
                showDialogConfirmDeleteOrder = true
            }

        )
    }


    if (showDialogConfirmDeleteOrder) {
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_order_title),
            dialogSubTitle = stringResource(
                R.string.msg_delete_order_list_body,
                order.orderName
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
        ReportsDialog(
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
        null -> Black
    }


@Composable
private fun getUpdateStatusList(order: Order) =
    stringResource(
        if (order.orderBuy) R.string.msg_in_progress_order_body else R.string.msg_complete_order_body,
        order.orderName
    )


@Composable
fun MinimalListItem(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            // Apply the RoundedCornerShape with a large radius
            .background(
                color = Color.Blue.copy(alpha = 0.7f),
                shape = RoundedCornerShape(percent = 50) // Or use a specific dp value like 50.dp
            )
            .padding(horizontal = 24.dp, vertical = 12.dp) // Add padding for content
    ) {
        Text(
            text = "This is a pill-shaped box",
            color = Color.White
        )
    }
    // Optional: Add a subtle indicator like an arrow or icon
    //
    //
    //
    //
    //
    //

}


@Preview
@Composable
fun OrderMainItemPreview() {
    ReportsGoTheme {
        OrderItem(
            Order(
                orderSeason = Season.FALL,
                orderName = "name",
                orderId = "1746568014",
                orderBuy = false
            ),
            onMainOrderClick = {},
            onDeleteOrderClick = {}, onUpdateStatusOrderClick = {})
    }

}
