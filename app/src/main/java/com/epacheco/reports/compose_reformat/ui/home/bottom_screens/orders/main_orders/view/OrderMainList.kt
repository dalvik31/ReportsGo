package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.view

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.orders.OrderMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderList(
    orderMains: List<OrderMain>,
    status: String,
    isRefreshing: Boolean = false,
    onDeleteOrderClick: ((String) -> Unit)? = null,
    onUpdateStatusOrderClick: ((OrderMain) -> Unit)? = null,
    onOrderClick: ((OrderMain) -> Unit)? = null,
    onRefresh: (() -> Unit)? = null,
    showImgEmptyList: Boolean?,
) {
    if (orderMains.isEmpty() && showImgEmptyList == false) {
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
                text = stringResource(R.string.list_order_empty, status),
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
                items(orderMains) { order ->
                    OrderMainItem(
                        orderMain = order,
                        onMainOrderClick = {
                            onOrderClick?.invoke(order)
                        },
                        onDeleteOrderClick = {
                            onDeleteOrderClick?.invoke(it)
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