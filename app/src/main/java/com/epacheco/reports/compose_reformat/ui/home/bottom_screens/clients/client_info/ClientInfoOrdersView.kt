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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.FinanceItem
import com.epacheco.reports.compose_reformat.general_components.OrderItem
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.sales.Sale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientInfoOrdersView(
    clientOrders: List<Order> = emptyList(),
) {


    Column {
        if (clientOrders.isEmpty()) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_vector_empty_orders),
                    contentDescription = null
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),

                ) {
                items(clientOrders) {
                    OrderItem(order = it, showEditBtn = false)
                }
            }
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientInfoOrdersViewPreview() {
    ClientInfoOrdersView()
}
