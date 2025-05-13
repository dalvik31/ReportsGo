package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.ProductItem
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    productList: List<Product> = emptyList(),
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onGoProductDetailClick: (String?) -> Unit
) {
    Column {
        Header(
            title = pluralStringResource(
                R.plurals.title_products,
                count = productList.size,
                productList.size,
            ),
            backgroundToolbar = Color.Transparent,
            titleColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {
                onGoProductDetailClick.invoke(null)
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add)
        )

        SearchBarElement(modifier = Modifier.padding(horizontal = 16.dp),searchText = ""){}

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onRefresh?.invoke() },
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(productList) { product ->
                    ProductItem(product) {
                        onGoProductDetailClick.invoke(product.productName)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        ProductsView() {

        }
    }
}
