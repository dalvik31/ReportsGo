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
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun      ClientInfoView(
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


    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh?.invoke() },
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.onPrimary,
                color = MaterialTheme.colorScheme.primary,
                state = state
            )
        }
    ) {
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
                    R.string.orders_title
                )

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

                    1 -> ClientInfoOrdersView(
                        clientOrders,
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ClientInfoViewPreview() {
    ReportsGoTheme() { ClientInfoView() }

}
