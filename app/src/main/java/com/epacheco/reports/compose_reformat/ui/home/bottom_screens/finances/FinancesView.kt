package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.FinanceItem
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.general_components.MoneyItem
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.Finances.PaymentType
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesView(
    orderMainMainList: List<Sale> = emptyList(),
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onSelectDatePressed: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    initialDate: String? = null,
    finalDate: String? = null,
) {
    val state = rememberPullToRefreshState()

    Column {
        Header(
            title = stringResource(
                R.string.finances_title,
            ),
            titleColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {
                onBackPressed?.invoke()
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_error),
        )

        /*SecondaryItem(
            modifier = Modifier.fillMaxWidth(),
            text = finalDate,
            secondaryText = stringResource(R.string.finances_final_date),
            icon = R.drawable.ic_vector_activity,
            customHeight = 60.dp,
            onItemPressed = {
                openFinalDate?.invoke()
            }
        )*/
        /*TextDivider(
            textDivider = "${orderMainMainList.size} Productos vendidos",
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp)
        )*/


        val totalSales =
            orderMainMainList.asSequence().filter { it.paymentType  == PaymentType.PAY || it.paymentType  == PaymentType.CASH }.sumOf { it.productPriceSale }
        val totalBuy =
            orderMainMainList.asSequence().filter { it.paymentType  == PaymentType.PAY || it.paymentType  == PaymentType.CASH }.sumOf { it.productPriceBuy }






        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            MoneyItem(
                text = "Productos",
                icon = R.drawable.ic_vector_products,
                amount = orderMainMainList.size.toString(),
                isAmount = false
            )
            MoneyItem(text = "Ventas", icon = R.drawable.ic_earns, amount = totalSales.toString())
            MoneyItem(text = "Inversion", icon = R.drawable.ic_sales, amount = totalBuy.toString())
            MoneyItem(
                text = "Ganancias",
                icon = R.drawable.ic_vector_sale,
                amount = (totalSales - totalBuy).toString()
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {

            TextDivider(
                textDivider = "${initialDate} a ${finalDate}",
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 12.dp)
                    .weight(1f)
                    .clickable {
                        onSelectDatePressed?.invoke()
                    }
            )


            IconButton(
                modifier = Modifier,
                onClick = {
                    onSelectDatePressed?.invoke()
                }) {

                Icon(
                    painter = painterResource(R.drawable.ic_vector_activity),
                    contentDescription = null,
                )
            }

        }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            modifier = Modifier.padding(top = 10.dp),
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                items(orderMainMainList) { sale ->
                    FinanceItem(sale = sale)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        FinancesView()
    }
}
