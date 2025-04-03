package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


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


@Preview
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        ProductsView {}
    }

}