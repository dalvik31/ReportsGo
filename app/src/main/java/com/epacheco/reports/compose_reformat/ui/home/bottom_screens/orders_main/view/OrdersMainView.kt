package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.launch

@Composable
fun OrderMainView(
    orderMainMainList: List<OrderMain> = emptyList(),
    showImgEmptyList: Boolean? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onOrderClick: ((OrderMain) -> Unit)? = null,
    onCreateOrderMainClick: (() -> Unit)? = null,
    onDeleteOrderClick: ((String) -> Unit)? = null,
    onUpdateStatusOrderClick: ((OrderMain) -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    Column {
        /*TextDivider(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            textDivider = pluralStringResource(
                R.plurals.title_main_orders,
                count = orderMainMainList.size,
                orderMainMainList.size
            ),
            fontSize = 14.sp
        )*/
        //OrderMainBanner()
        PrimaryButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            textButton = stringResource(id = R.string.create_new_list_orders).uppercase(),
            iconBtn = R.drawable.ic_vector_add,
        ) {
            onCreateOrderMainClick?.invoke()
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
                    text = stringResource(R.string.list__main_orders_empty),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        } else {
            val tabs = listOf(
                stringResource(
                    OrderStatus.IN_PROGRESS.orderStatusName, orderMainMainList.filter { it.orderStatus == OrderStatus.IN_PROGRESS }.size,),
                stringResource(
                    OrderStatus.DONE.orderStatusName, orderMainMainList.filter { it.orderStatus == OrderStatus.DONE }.size,),

                )
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                        text = { Text(title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSpacing = 16.dp
            ) { page ->
                when (page) {
                    0 -> OrderList(
                        orderMainMainList.filter { it.orderStatus == OrderStatus.IN_PROGRESS },
                        stringResource(OrderStatus.IN_PROGRESS.orderStatusName),
                        isRefreshing = isRefreshing,
                        onDeleteOrderClick = onDeleteOrderClick,
                        onUpdateStatusOrderClick = onUpdateStatusOrderClick,
                        onOrderClick = onOrderClick,
                        onRefresh = onRefresh,
                        showImgEmptyList = isRefreshing
                    )

                    1 -> OrderList(
                        orderMainMainList.filter { it.orderStatus == OrderStatus.DONE },
                        stringResource(OrderStatus.DONE.orderStatusName),
                        isRefreshing = isRefreshing,
                        onDeleteOrderClick = onDeleteOrderClick,
                        onUpdateStatusOrderClick = onUpdateStatusOrderClick,
                        onOrderClick = onOrderClick,
                        onRefresh = onRefresh,
                        showImgEmptyList = isRefreshing
                    )
                }
            }


        }


    }
}

@Preview
@Composable
fun OrderMainViewPreview() {
    ReportsGoTheme {
        OrderMainView()
    }
}