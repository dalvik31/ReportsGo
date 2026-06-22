package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.select_orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.general_components.OrderMainItem
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton

@Composable
fun SelectOrderView(
    clientOrders: List<OrderMain> = emptyList(),
    onOrderSelected: ((OrderMain) -> Unit)? = null,
    onCreateOrderClick: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    selectOrderMode: Boolean = false
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
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

            Card(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 10.dp)) {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource( if(selectOrderMode) R.string.select_list_main_order else R.string.select_list_main),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

            }

            if (clientOrders.isEmpty()) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_vector_empty_main_orders),
                        contentDescription = null,
                    )
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.list__main_orders_empty),
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

                    items(clientOrders) { orderMain ->
                        OrderMainItem(orderMain = orderMain, onMainOrderClick = {
                            onOrderSelected?.invoke(orderMain)
                        }, onDeleteOrderClick = {}) { }
                    }
                }
            }


        }
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {

            PrimaryButton(
                textButton = stringResource(R.string.create_new_list_orders),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                onCreateOrderClick?.invoke()
            }
        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectOrderViewPreview() {
    SelectOrderView()
}
