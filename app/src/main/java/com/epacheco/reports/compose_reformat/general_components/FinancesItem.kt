package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.SpringColor
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.extensions.stockColor


@Composable
fun FinanceItem(
    sale: Sale,
) {

    Column(modifier = Modifier.alpha(if (sale.isCancelSale) 0.4f else 1f)) {


        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_vector_sale),
                colorFilter = ColorFilter.tint(if (sale.creditSale) RedDark else GreenColor),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )

            Text(
                text = stringResource(if (sale.creditSale) R.string.credit_transaction else R.string.cash_transaction),
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
                .height(100.dp)
                .clickable {
                    //onProductClick.invoke(product)
                },
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                AvatarWithIndicator(
                    sale.imgProduct,
                    indicatorRes = R.drawable.icon_person, // Replace with your badge drawable
                    tintSaleIndicator = if (sale.creditSale) RedDark else SpringColor,
                    modifier = Modifier.padding(all = 8.dp),
                )

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {


                        Row {
                            Text(
                                sale.productName,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(start = 8.dp, top = 8.dp),
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 14.sp
                            )

                            Text(
                                stringResource(R.string.lbl_price_sale, sale.productPriceSale),
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(topStart = 10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),


                            ) {
                            Text(
                                text = sale.saleId,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                    }
                }


            }

        }
    }

}


@Preview
@Composable
fun FinanceItemPreview() {
    ReportsGoTheme {
        FinanceItem(Sale(productName = "Producto", productPriceSale = 34.0, saleId = "31/Oct/2025"))
    }

}