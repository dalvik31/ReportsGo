package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.ProductItem
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun ProductsScreen(
    ordersViewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>()
) {


 /*   result

    when(result){
        is Resource.Failure -> Log.e("aqui","estamoss: ${result.exception}")
        is Resource.Success -> TODO()
        Resource.Waiting -> TODO()
        null -> TODO()
    }
        if (result()) {
            EmptyLocations()
        } else {
            LazyColumnFor(
                modifier = Modifier.padding(innerPadding),
                items = result.value.data.orEmpty()
            ) { location ->
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = location.label, style = MaterialTheme.typography.h3)
                }
            }
        }*/

   /* val orderResponse = ordersViewModel.productsFlow.collectAsState()
    val data by remember { ordersViewModel.productsFlow }.collectAsStateWithLifecycle(null)



    data?.let {
        when (it) {
            is Resource.Failure -> {
                it.exception?.let {
                    Log.e("aqui", "ProductsScreen ${it.message}")
                }
            }

            Resource.Waiting -> Loader(false, stringResource(R.string.search_products))
            is Resource.Success -> {
                Column {
                    TextDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        textDivider = pluralStringResource(
                            R.plurals.title_products,
                            count = it.result.size,
                            it.result.size
                        ),
                        fontSize = 14.sp
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Transparent),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(it.result) { product ->
                            ProductItem(
                                img = product.urlImage,
                                title = product.productName,
                                price = product.productPriceSale,
                                stock = product.inStock
                            )
                        }
                    }
                }
            }

        }

    }*/

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        ProductsScreen()
    }

}