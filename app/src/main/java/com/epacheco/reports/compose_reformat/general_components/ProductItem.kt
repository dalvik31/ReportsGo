package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.extensions.stockColor


@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit
) {

    Column(modifier = Modifier.alpha(if(product.inStock > 0) 1f else 0.4f)) {

        Row(
            Modifier.fillMaxWidth().padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_circle_24),
                colorFilter = ColorFilter.tint(product.inStock.stockColor()),
                contentDescription = null,
                modifier = Modifier.size(8.dp)
            )

            Text(
                text = stringResource(R.string.lbl_stock_sale, product.inStock),
                modifier = Modifier
                    .height(20.dp)
                    .padding(start = 8.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Light
                )
            )
        }


        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
                .wrapContentHeight()
                .clickable {
                    onProductClick.invoke(product)
                },
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(product.urlImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                )

                Text(
                    product.productName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp,top = 8.dp),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )

                Text(
                    stringResource(R.string.lbl_price_sale, product.productPriceSale),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .align(Alignment.End),
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }

}

@Preview
@Composable
fun FavoriteCollectionElementPreview() {
    ReportsGoTheme {
        ProductItem(Product(productName = "Producto", inStock = 0)) {

        }
    }

}