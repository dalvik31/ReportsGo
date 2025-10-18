package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.SearchBarElement
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    productList: List<Product> = emptyList(),
    onInputNameChanged: ((String) -> Unit)? = null,
    inputName: String? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onGoProductDetailClick: ((String?, Int?) -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null
) {
    val state = rememberPullToRefreshState()

    Column {
        Header(
            title = pluralStringResource(
                R.plurals.title_products,
                count = productList.size,
                productList.size,
            ),
            titleColor = MaterialTheme.colorScheme.primary,
            onRightIconClicked = {
                onGoProductDetailClick?.invoke(null,null)
            },
            tintImageRight = MaterialTheme.colorScheme.primary,
            rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_add),
            onProfileClicked = { onNavigateToProfile?.invoke() },
            tintIconProfile = MaterialTheme.colorScheme.primary
        )

        SearchBarElement(
            modifier = Modifier.padding(horizontal = 16.dp),
            searchHintText = stringResource(id = R.string.lbl_search_product_hint),
            searchText = inputName ?: "",

        ) {
            onInputNameChanged?.invoke(it)
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

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

            if(productList.isEmpty()){
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_vector_products_empty),
                        contentDescription = null
                    )
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.msg_zero_products),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }else{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(productList) { product ->
                        ProductItem(product) {
                            onGoProductDetailClick?.invoke(product.productId,product.inStock)
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        ProductsView()
    }
}
