package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.Yellow

@Composable
fun OrderMainView(
    orderMainList: List<Order> = emptyList(),
    showImgEmptyList: Boolean? = null,
    onMainOrderClick: ((Order) -> Unit)? = null,
    onDeleteOrderClick: ((String) -> Unit)? = null
) {
    Column {
        OrderMainBanner()

        PrimaryButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            textButton = stringResource(id = R.string.create_new_list_orders).uppercase(),
            iconBtn = R.drawable.ic_vector_add,
            colorBackground = Yellow,
        ) {

        }

        TextDivider(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            textDivider = pluralStringResource(
                R.plurals.title_orders,
                count = orderMainList.size,
                orderMainList.size
            ),
            fontSize = 14.sp
        )

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
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
            ) {
                items(orderMainList) { order ->
                    OrderMainItem(
                        order = order,
                        onMainOrderClick = {
                            onMainOrderClick?.invoke(order)
                        },
                        onDeleteOrderClick = {
                            onDeleteOrderClick?.invoke(order.dateOrder)

                        }
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