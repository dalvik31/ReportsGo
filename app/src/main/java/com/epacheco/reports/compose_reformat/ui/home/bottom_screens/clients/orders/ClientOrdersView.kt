package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.view.OrderMainItem

@Composable
fun ClientOrdersView(
    clientOrders: List<OrderMain> = emptyList(),
    onOrderSelected: ((OrderMain) -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {

    Column {
        Header(
            text = stringResource(R.string.lbl_title_orders),
            backgroundToolbar = Color.Transparent,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },

            textColor = MaterialTheme.colorScheme.primary,

            tintImageRight = MaterialTheme.colorScheme.primary
        )

        Card(modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()) {
            Column(modifier = Modifier.padding(vertical = 10.dp)) {

                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.select_list_main),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),

            ) {

            items(clientOrders) { orderMain ->
                OrderMainItem(orderMain = orderMain, onMainOrderClick = {
                    onOrderSelected?.invoke(orderMain)
                }, onDeleteOrderClick = {}) { }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClientOrdersViewPreview() {
    ClientOrdersView()
}
