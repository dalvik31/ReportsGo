package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.model.Finances.PaymentType
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White


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
                    modifier = modifier
                        .padding(
                            top = 10.dp,
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 5.dp
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
                            avatarRes = if (sale.paymentType == PaymentType.PAY) R.drawable.ic_vector_sale else null,
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
                                Text(
                                    modifier = modifier,
                                    text = sale.saleId,
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

}


@Composable
private fun getTypeSale(saleType: PaymentType): String? {
    return when (saleType) {
        PaymentType.PAY -> stringResource(R.string.option_pay)
        PaymentType.CREDIT -> stringResource(R.string.option_credit)
        PaymentType.CASH -> stringResource(R.string.option_cash)
        PaymentType.UNKNOWN -> stringResource(R.string.option_unknown)
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