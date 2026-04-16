package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrderList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientInfoView(
    clientTransaction: List<Sale> = emptyList(),
    clientOrders: List<Order> = emptyList(),
    clientName: String? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    Column {
        Header(
            text = stringResource(R.string.operations_title, clientName ?: ""),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            textColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )




            val tabs = listOf(
                stringResource(R.string.transactions_title),
                stringResource(
                    R.string.orders_title)

            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                containerColor = Color.Transparent,
                divider = {}
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
                    0 -> ClientInfoTransactionsView(clientTransaction)

                    1 -> ClientInfoOrdersView(clientOrders)
                }
            }


            /* if (clientTransaction.isEmpty()) {
                 Column(
                     Modifier.fillMaxSize(),
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     Image(
                         painter = painterResource(R.drawable.ic_sales_empty),
                         contentDescription = null
                     )
                     Text(
                         color = MaterialTheme.colorScheme.primary,
                         text = stringResource(R.string.msg_info_client_not_found),
                         style = MaterialTheme.typography.bodySmall,
                         modifier = Modifier.padding(top = 12.dp)
                     )
                 }
             } else {
                 LazyColumn(
                     modifier = Modifier
                         .fillMaxSize()
                         .padding(20.dp),
                     verticalArrangement = Arrangement.spacedBy(20.dp),

                     ) {
                     items(clientTransaction) {
                         FinanceItem(sale = it)
                     }
                 }
             }*/




    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientInfoViewPreview() {
    ClientInfoView()
}
