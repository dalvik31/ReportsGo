package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main.view.OrderMainItem
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersView(
    orderList: List<Order> = emptyList(),
    showImgEmptyList: Boolean? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onOrderClick: ((Order) -> Unit)? = null,
    onCreateOrderClick: (() -> Unit)? = null,
    onDeleteOrderClick: ((String) -> Unit)? = null,
    onUpdateStatusOrderClick: ((Order) -> Unit)? = null
) {
    Column {
        TextDivider(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            textDivider = pluralStringResource(
                R.plurals.title_orders,
                count = orderList.size,
                orderList.size
            ),
            fontSize = 14.sp
        )

        PrimaryButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            textButton = stringResource(id = R.string.create_new_order).uppercase(),
            iconBtn = R.drawable.ic_vector_add,
        ) {
            onCreateOrderClick?.invoke()
        }


        if (showImgEmptyList == true) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_vector_empty_orders),
                    contentDescription = null
                )
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.list_orders_empty),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        } else {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { onRefresh?.invoke() },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                ) {
                    items(orderList) { order ->
                        OrderItem(
                            orderMain = order,
                            onMainOrderClick = {
                                onOrderClick?.invoke(order)
                            },
                            onDeleteOrderClick = {
                                onDeleteOrderClick?.invoke(order.orderId)
                            },
                            onUpdateStatusOrderClick = {
                                onUpdateStatusOrderClick?.invoke(order)
                            }
                        )
                    }
                }
            }

        }


    }
}

@Preview
@Composable
fun OrderMainViewPreview() {
    ReportsGoTheme {
        OrdersView(showImgEmptyList = true)
    }
}