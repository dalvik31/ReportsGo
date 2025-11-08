package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import androidx.compose.foundation.Image
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
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.FinanceItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.model.Finances.Sale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientInfoView(
    clientTransaction: List<Sale> = emptyList(),
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val state = rememberPullToRefreshState()
    Column {
        Header(
            text = stringResource(R.string.transactions_title),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            textColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = state,
            onRefresh = { onRefresh?.invoke() },
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
            if(clientTransaction.isEmpty()){
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
            }else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),

                    ) {
                    items(clientTransaction) {
                        FinanceItem(sale = it)
                    }
                }
            }

        }



    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientDetailViewPreview() {
    ClientInfoView()
}
