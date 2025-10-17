package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.ProductItem
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE2


@Composable
fun SalesView(
    inputClient: String? = null,
    inputProduct: String? = null,
    listProductCart: List<Product>? = null,
    totalSale: Double? = null,
    onInputClientChanged: (() -> Unit)? = null,
    onInputProductChanged: (() -> Unit)? = null,
    onNavigateToFinances: (() -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    onIncrementProductToCar: ((Product) -> Unit)? = null,
    onSubtractProductToCar: ((Product) -> Unit)? = null,
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Column {
            Header(
                title = stringResource(R.string.generate_sale),
                titleColor = MaterialTheme.colorScheme.primary,
                rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_activity),
                onRightIconClicked = {
                    onNavigateToFinances?.invoke()
                },
                tintImageRight = MaterialTheme.colorScheme.primary,
                onProfileClicked = { onNavigateToProfile?.invoke() },
                tintIconProfile = MaterialTheme.colorScheme.primary
            )

            InputTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        onInputClientChanged?.invoke()
                    },
                textHint = stringResource(R.string.select_client),
                textValue = inputClient ?: "",
                enable = false
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            PrimaryButton(
                textButton = stringResource(R.string.select_product),
                modifier = Modifier.padding(horizontal = 40.dp)
            ){
                onInputProductChanged?.invoke()
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            if (!listProductCart.isNullOrEmpty()) {
                TextDivider(textDivider = "Carrito de compras")
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    contentPadding = PaddingValues(bottom = 70.dp)
                ) {
                    items(listProductCart) { product ->
                        SaleItem(
                            product = product,
                            onSelectItem = {},
                            onIncrementProductToCar = {
                                onIncrementProductToCar?.invoke(product)
                            },
                            onSubtractProductToCar = {
                                onSubtractProductToCar?.invoke(product)
                            }
                        )
                    }

                }

            }

            Spacer(Modifier.weight(1f))

        }
        totalSale?.let {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total $${it}",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,

                        )
                    Text(
                        text = "|",
                        fontSize = 18.sp,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Text(
                        text = "Pagar",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .clickable {

                                //navController?.navigate(NavHostScreens.PASSWORD.route)
                            },
                        textAlign = TextAlign.Right,
                        color = RedDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable()
fun SalesViewPreview() {
    ReportsGoTheme {
        SalesView(totalSale = 34.toDouble())
    }
}
