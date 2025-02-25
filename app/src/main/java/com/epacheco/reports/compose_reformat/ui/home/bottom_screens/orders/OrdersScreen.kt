package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItemPreview
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel<OrdersViewModel>()
) {
    val orderResponse = ordersViewModel.ordersFlow.collectAsState()


    orderResponse.value?.let {
        when (it) {
            is Resource.Failure -> {
                it.exception?.let {
                    Log.e("aqui", "OrdersScreen vamooos: ${it.message}")
                }
            }

            Resource.Loading -> Loader(false, stringResource(R.string.search_orders))
            is Resource.Success -> {
                Column {
                    TextDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        textDivider = pluralStringResource(
                            R.plurals.title_orders,
                            count = it.result.size,
                            it.result.size
                        ),
                        fontSize = 14.sp
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent)
                    ) {
                        items(it.result) { order ->
                            ListAnimationItem(
                                title = order.msjOrder,
                                body = order.nameOrder,
                            )
                        }
                    }
                }
            }

        }

    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ReportsGoTheme {
        OrdersScreen()
    }

}
