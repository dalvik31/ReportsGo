package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.extensions.stockColor

@Composable
fun SaleItem(
    modifier: Modifier = Modifier,
    product: Product? = null,
    onIncrementProductToCar: ((Product) -> Unit)? = null,
    onSubtractProductToCar: ((Product) -> Unit)? = null,
    onSelectItem: () -> Unit,
) {

    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp)
            .height(100.dp)
            .wrapContentHeight()
            .clickable {
                onSelectItem.invoke()
            },
        colors = CardColors(
            contentColor = White,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    ) {

        Row() {
            Image(
                painter = rememberAsyncImagePainter(product?.urlImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .width(100.dp)
                    .height(100.dp),
            )

            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        product?.productName ?: "",
                        modifier = modifier
                            .wrapContentWidth()
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = modifier.weight(1f))

                    Text(
                        stringResource(R.string.lbl_price_sale, product?.productPriceSale ?: 0),
                        modifier = modifier
                            .padding(end = 14.dp),
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,

                        )


                }
                Spacer(modifier = modifier.weight(1f))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_simple_dot),
                        colorFilter = ColorFilter.tint(
                            product?.inStock?.stockColor() ?: Color.Transparent
                        ),
                        contentDescription = null,
                        modifier = modifier.size(8.dp),
                    )
                    Text(
                        text = stringResource(R.string.lbl_stock_sale, product?.inStock ?: 0),
                        modifier = modifier
                            .padding(start = 8.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Light
                        ),
                    )
                    Spacer(modifier = modifier.weight(1f))
                    IconButton(onClick = {
                        product?.let {
                            onSubtractProductToCar?.invoke(it)
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.RemoveCircleOutline,
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "${product?.auxStock}",
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Light
                        ),
                    )

                    IconButton(onClick = {
                        product?.let {
                            onIncrementProductToCar?.invoke(it)
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircleOutline,
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }
        }

    }

}


@Preview
@Composable
fun SaleItemPreview() {
    SaleItem(Modifier, Product(productName = "nombre producto", productPriceSale = 95.0)) {}
}

