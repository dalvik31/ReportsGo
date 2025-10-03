package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersView(
    orderList: List<Order> = emptyList(),
    showImgEmptyList: Boolean? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    onOrderClick: ((Order) -> Unit)? = null,
    onCreateOrderClick: (() -> Unit)? = null,
    onDeleteOrderClick: ((String) -> Unit)? = null,
    onUpdateStatusOrderClick: ((Order) -> Unit)? = null,
    nameOrderMain: String? = null
) {
    Column(Modifier.background(color = Color.Transparent)) {

        Header(
            title = pluralStringResource(
                R.plurals.title_orders,
                count = orderList.size,
                orderList.size
            ),
            titleColor = MaterialTheme.colorScheme.primary,
            onLeftIconClicked = { onBackPressed?.invoke() },
            leftImageVector = Icons.Default.ArrowBackIosNew,
            tintImageLeft = MaterialTheme.colorScheme.primary,
            onRightIconClicked = { onCreateOrderClick?.invoke() },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add)
        )
        nameOrderMain?.let {
            TextDivider(modifier = Modifier.padding(horizontal = 12.dp).padding(bottom = 16.dp), textDivider = it.uppercase(), fontSize = 14.sp)
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(orderList) { order ->
                        OrderItem(
                            order = order,
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
                /*LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                ) {
                    items(orderList) { order ->
                        OrderItem(
                            order = order,
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
                }*/
            }

        }


    }
}

@Preview
@Composable
fun OrderMainViewPreview() {
    ReportsGoTheme {
        OrdersView(showImgEmptyList = true, nameOrderMain = "Mi lista")
    }
}