package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.extensions.StockColor


@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .background(Color.Black, shape = RoundedCornerShape(10.dp))
            .clickable {
                onProductClick.invoke(product)
            }
    ) {

        AsyncImage(
            model = product.urlImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .alpha(0.8f),
        )


        Text(
            product.productName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = White,
            fontSize = 16.sp
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(
                stringResource(R.string.lbl_price_sale, product.productPriceSale),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.End),
                fontSize = 14.sp,
                color = White,
            )

            Text(
                stringResource(R.string.lbl_stock_sale, product.inStock),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .background(product.inStock.StockColor(), shape = RoundedCornerShape(20.dp)),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = White,
            )
        }

    }

}

@Preview
@Composable
fun FavoriteCollectionElementPreview() {
    ReportsGoTheme {
        ProductItem(Product()) {

        }
    }

}