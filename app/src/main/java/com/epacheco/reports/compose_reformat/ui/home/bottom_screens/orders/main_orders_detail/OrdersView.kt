package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.epacheco.reports.compose_reformat.general_components.SecondaryItem
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE4
import java.util.Locale

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
    onUpdateStatusOrderClick: ((Order) -> Unit)? = null,
    nameOrderMain: String? = null,
    mainOrderId: String? = null
) {
    Column(Modifier.background(color = Color.Transparent)) {

        Header(
            text = pluralStringResource(
                R.plurals.title_orders,
                count = orderList.size,
                orderList.size
            ),
            textColor = MaterialTheme.colorScheme.primary,
            onLeftIconClicked = { onBackPressed?.invoke() },
            leftImageVector = Icons.Default.ArrowBackIosNew,
            tintImageLeft = MaterialTheme.colorScheme.primary,
            onRightIconClicked = { onCreateOrderClick?.invoke() },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add)
        )
        nameOrderMain?.let { nameOrder ->
            TextDivider(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 16.dp),
                text = nameOrder.ifEmpty {
                    DateUtils.dateFormat(
                        mainOrderId.toString(),
                        FORMAT_DATE4
                    )
                }
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                fontSize = 14.sp
            )
        }



        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onRefresh?.invoke() },
        ) {
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(orderList) { order ->
                        SecondaryItem(
                            text = order.orderName,
                            contentText = stringResource(
                                R.string.description_order,
                                order.orderDescription,
                                order.orderSize,
                                order.orderColor,
                                order.orderGender,
                                order.orderClientName ?: ""
                            ),
                            secondaryText = DateUtils.dateFormat(order.orderId, FORMAT_DATE2),
                            strikeThrough = order.orderBuy,
                            onClick = {
                                onUpdateStatusOrderClick?.invoke(order)
                            },
                            onLongClick = {
                                onOrderClick?.invoke(order)
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
        OrdersView(showImgEmptyList = true, nameOrderMain = "Mi lista")
    }
}