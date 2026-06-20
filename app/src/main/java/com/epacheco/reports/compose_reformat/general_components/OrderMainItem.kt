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
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsOptionDialog
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE4
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE5
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE7
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE8
import com.epacheco.reports.compose_reformat.utils.Utils
import com.epacheco.reports.compose_reformat.utils.Utils.getCardBackground


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrderMainItem(
    orderMain: OrderMain,
    onMainOrderClick: (OrderMain) -> Unit,
    onDeleteOrderClick: (String) -> Unit,
    onUpdateStatusOrderClick: (OrderMain) -> Unit

) {

    var showDialogOptionsOrder by remember { mutableStateOf(false) }
    var showDialogConfirmDeleteOrder by remember { mutableStateOf(false) }
    var showDialogConfirmCompleteOrder by remember { mutableStateOf(false) }
    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .combinedClickable(
                    onClick = {
                        onMainOrderClick.invoke(orderMain)
                    },
                    onLongClick = { showDialogConfirmDeleteOrder = true },
                ),
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            val numOrders = orderMain.orderLists?.size ?: 0
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(
                            getCardBackground(orderMain.orderSeason)
                        )
                )
                Box(contentAlignment = Alignment.TopEnd) {
                    if (!orderMain.orderId.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(bottomStart = 10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),

                            ) {

                            Text(
                                text = stringResource(
                                    R.string.order_date_week_string,
                                    DateUtils.dateFormat(orderMain.orderId, FORMAT_DATE8)
                                ),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall,

                                )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
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
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {


                                Text(
                                    color = MaterialTheme.colorScheme.primary,

                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 8.dp),
                                    text = orderMain.nameOrder.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            java.util.Locale.ROOT
                                        ) else it.toString()
                                    }
                                        .ifEmpty {

                                            stringResource(
                                                R.string.order_date_week, DateUtils.dateFormat(
                                                    orderMain.orderId,
                                                    FORMAT_DATE7
                                                )
                                            )


                                        },
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Spacer(modifier = Modifier.weight(1f))

                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                                    .padding(end = 12.dp, start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {

                                LinearProgressIndicator(
                                    modifier = Modifier.wrapContentWidth(),
                                    gapSize = (1).dp,
                                    progress = { orderMain.geProgressList() },
                                    drawStopIndicator = {
                                        drawStopIndicator(
                                            drawScope = this,
                                            stopSize = ProgressIndicatorDefaults.LinearTrackStopIndicatorSize,
                                            color = Color.Transparent,
                                            strokeCap = StrokeCap.Round,
                                        )
                                    }

                                )
                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    modifier = Modifier.wrapContentWidth(),
                                    text = pluralStringResource(
                                        R.plurals.num_orders,
                                        count = numOrders,
                                        numOrders
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )


                            }

                        }

                    }
                }
            }

        }
    }



    if (showDialogOptionsOrder) {
        ReportsOptionDialog(
            imgDialog = R.drawable.ic_vector_order,
            dialogTitle = stringResource(R.string.msg_dialog_options_title),
            dialogSubTitle = stringResource(R.string.msg_dialog_options_body),
            firstOptionText = stringResource(if (orderMain.orderStatus == OrderStatus.IN_PROGRESS) R.string.msg_dialog_check_complete else R.string.msg_dialog_check_in_progress),
            secondOptionText = stringResource(R.string.msg_dialog_delete_option),
            onDismissRequest = { showDialogOptionsOrder = false },
            onFirstConfirmation = {
                showDialogOptionsOrder = false
                onUpdateStatusOrderClick.invoke(orderMain)
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
            dialogTitle = stringResource(R.string.msg_delete_main_order_title),
            dialogSubTitle = stringResource(
                R.string.msg_delete_main_order_list_body,
                orderMain.nameOrder.ifEmpty {

                    stringResource(
                        R.string.order_date_week, DateUtils.dateFormat(
                            orderMain.orderId,
                            FORMAT_DATE7
                        )
                    )


                }
            ),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteOrder = false },
            onConfirmation = {
                showDialogConfirmDeleteOrder = false
                onDeleteOrderClick.invoke(orderMain.dateOrder.ifEmpty { orderMain.orderId })
            }
        )
    }

    if (showDialogConfirmCompleteOrder) {
        ReportsDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.msg_update_state_order_title),
            dialogSubTitle = getUpdateStatusList(orderMain),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmCompleteOrder = false },
            onConfirmation = {
                showDialogConfirmCompleteOrder = false
                onUpdateStatusOrderClick.invoke(orderMain)
            }
        )
    }

}


@Composable
private fun getUpdateStatusList(orderMain: OrderMain) =
    stringResource(
        when (orderMain.orderStatus) {
            OrderStatus.IN_PROGRESS -> R.string.msg_complete_main_order_body
            OrderStatus.DONE -> R.string.msg_in_progress_main_order_body
        }, orderMain.nameOrder
    )


@Preview
@Composable
fun OrderMainItemPreview() {
    OrderMainItem(
        OrderMain(orderId = "0", nameOrder = "name", orderSeason = Season.SPRING),
        onMainOrderClick = {},
        onDeleteOrderClick = {}, onUpdateStatusOrderClick = {})
}
