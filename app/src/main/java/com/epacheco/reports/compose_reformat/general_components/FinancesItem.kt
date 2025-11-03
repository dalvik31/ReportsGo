package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.Finances.PaymentType
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.SpringColor
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.Utils
import com.epacheco.reports.compose_reformat.utils.extensions.stockColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinanceItem(
    sale: Sale,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.alpha(if (sale.isCancelSale) 0.4f else 1f)) {


        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardColors(
                contentColor = White,
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {

            Box {

                val saleType = getTypeSale(saleType = sale.paymentType)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                    saleType?.let { saleType ->
                        Text(
                            //sale.saleId,
                            text = saleType,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(bottomStart = 10.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                }



                Column(
                    modifier = modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp).combinedClickable(
                        onClick = {
                            //onClick?.invoke()
                        },
                        onLongClick = {
                            //onLongClick?.invoke()
                        },
                    )
                ) {

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {


                        AvatarWithIndicator(
                            avatarUrl = sale.imgProduct,
                            indicatorRes = R.drawable.baseline_circle_24,
                            avatarRes = if(sale.paymentType == PaymentType.PAY) R.drawable.ic_vector_sale else null,
                            avatarSize = 70.dp,
                            indicatorSize = 17.dp,
                            tintSaleIndicator = if (sale.paymentType == PaymentType.CREDIT) RedDark else GreenColor
                        )

                        Column(

                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 8.dp),
                        ) {
                            Text(
                                modifier = modifier,
                                text = sale.productName,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                modifier = modifier,
                                text = sale.nameClient,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )


                            Row(verticalAlignment = Alignment.Bottom) {
                                /*Image(
                                    painter = painterResource(R.drawable.ic_vector_sale),
                                    modifier = modifier.size(12.dp),
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(if (sale.creditSale) RedDark else GreenColor)
                                )*/
                                Text(
                                    modifier = modifier,
                                    text = sale.saleId,
                                    //text = stringResource(if (sale.creditSale) R.string.credit_transaction else R.string.cash_transaction),
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                                Text(
                                    "$${sale.productPriceSale}",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }


                        }

                    }


                }


            }
        }

    }
    /* Column(modifier = Modifier.alpha(if (sale.isCancelSale) 0.4f else 1f)) {


         Row(
             Modifier
                 .fillMaxWidth(),
             verticalAlignment = Alignment.CenterVertically,
         ) {

             Image(
                 painter = painterResource(R.drawable.ic_vector_sale),
                 colorFilter = ColorFilter.tint(if (sale.creditSale) RedDark else GreenColor),
                 contentDescription = null,
                 modifier = Modifier.size(16.dp)
             )

             Text(
                 text = stringResource(if (sale.creditSale) R.string.credit_transaction else R.string.cash_transaction),
                 modifier = Modifier
                     .height(20.dp)
                     .padding(start = 8.dp)
                     .wrapContentHeight(align = Alignment.CenterVertically),
                 color = MaterialTheme.colorScheme.secondary,
                 textAlign = TextAlign.Center,
                 style = MaterialTheme.typography.titleSmall.copy(
                     fontWeight = FontWeight.Light
                 )
             )
         }

         Card(
             modifier = Modifier
                 .padding(bottom = 16.dp)
                 .wrapContentHeight()
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

             Box(
                 modifier = Modifier
                     .fillMaxWidth(),
                 contentAlignment = Alignment.TopEnd


             ) {
                 Text(
                     modifier = Modifier.background(
                         MaterialTheme.colorScheme.onBackground,
                         RoundedCornerShape(topStart = 10.dp)
                     ) .padding(horizontal = 8.dp, vertical = 4.dp),
                     text = sale.saleId,
                     fontSize = 10.sp,
                     color = MaterialTheme.colorScheme.onPrimary,
                     style = MaterialTheme.typography.bodySmall,
                 )

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


                         }
                     }


                 }
             }



         }
     }*/

}


private fun getTypeSale(saleType: PaymentType): String? {
    return when (saleType) {
        PaymentType.PAY -> "Abono"
        PaymentType.CREDIT -> "Compra a credito"
        PaymentType.CASH -> "Compra en efectivo"
        PaymentType.UNKNOWN -> null
    }
}

@Preview
@Composable
fun FinanceItemPreview() {
    ReportsGoTheme {
        FinanceItem(
            Sale(
                productName = "Producto",
                nameClient = "cliente",
                productPriceSale = 34.0,
                saleId = "31/Oct/2025",
                paymentType = PaymentType.PAY
            )
        )
    }

}