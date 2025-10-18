package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.ProgressIndicatorDefaults.drawStopIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.Utils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesView(
    inputClient: Client? = null,
    inputProduct: String? = null,
    listProductCart: List<Product>? = null,
    totalSale: Double? = null,
    onInputClientChanged: (() -> Unit)? = null,
    onInputProductChanged: (() -> Unit)? = null,
    onNavigateToFinances: (() -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    onIncrementProductToCar: ((Product) -> Unit)? = null,
    onSubtractProductToCar: ((Product) -> Unit)? = null,
) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        Column {
            Header(
                title = stringResource(R.string.generate_sale),
                titleColor = MaterialTheme.colorScheme.primary,
                rightImageVector = ImageVector.vectorResource(R.drawable.ic_vector_activity),
                onRightIconClicked = {
                    onNavigateToFinances?.invoke()
                },
                tintImageRight = MaterialTheme.colorScheme.primary,
                onProfileClicked = { onNavigateToProfile?.invoke() },
                tintIconProfile = MaterialTheme.colorScheme.primary
            )

            /*InputTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        onInputClientChanged?.invoke()
                    },
                textHint = stringResource(R.string.select_client),
                textValue = inputClient ?: "",
                enable = false
            )*/

            Surface(color = Color.Transparent) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardColors(
                        contentColor = White,
                        containerColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                ) {


                    Box(contentAlignment = Alignment.TopEnd) {

                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onBackground,
                                    RoundedCornerShape(bottomStart = 10.dp)
                                )
                                .clickable {
                                    onInputClientChanged?.invoke()
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp),


                            ) {
                            Text(
                                text = if (inputClient?.name.isNullOrEmpty()) "Buscar cliente" else "Cambiar cliente",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall,

                                )
                        }

                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )

                        ) {
                            Column(
                                Modifier.wrapContentHeight(),
                                Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {


                                    IconButton({
                                    }) {
                                        Icon(
                                            painter = painterResource(R.drawable.new_ic_vector_comdin),
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = inputClient?.let {
                                                Utils.getClientDotBackground(
                                                    inputClient?.geProgressLimit() ?: 0f
                                                )
                                            } ?: run { Color.Transparent }

                                        )
                                    }

                                    Text(
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        text = inputClient?.name ?: "",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                }

                                Column {
                                    TextDivider(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .padding(horizontal = 20.dp),
                                        textDivider = "Credito"
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(vertical = 12.dp)
                                            .padding(end = 12.dp, start = 12.dp),
                                        verticalAlignment = Alignment.Bottom,
                                    ) {

                                        Column() {

                                            Text(
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .padding(bottom = 8.dp),
                                                text = "Utilizado $${inputClient?.limitUsed ?: 0}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.primary,
                                            )
                                            LinearProgressIndicator(
                                                modifier = Modifier.wrapContentWidth(),
                                                gapSize = (1).dp,
                                                progress = { inputClient?.geProgressLimit() ?: 0f },
                                                drawStopIndicator = {
                                                    drawStopIndicator(
                                                        drawScope = this,
                                                        stopSize = ProgressIndicatorDefaults.LinearTrackStopIndicatorSize,
                                                        color = Color.Transparent,
                                                        strokeCap = StrokeCap.Round,
                                                    )
                                                }

                                            )
                                        }


                                        Spacer(modifier = Modifier.weight(1f))


                                        Text(
                                            modifier = Modifier.wrapContentWidth(),
                                            text = "Total $${inputClient?.limit ?: 0}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary,
                                        )


                                    }
                                }


                            }

                        }
                    }
                }

            }


            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            PrimaryButton(
                textButton = stringResource(R.string.select_product),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                onInputProductChanged?.invoke()
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            if (!listProductCart.isNullOrEmpty()) {
                TextDivider(textDivider = stringResource(R.string.shopping_cart))
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    contentPadding = PaddingValues(bottom = 70.dp)
                ) {
                    items(listProductCart) { product ->
                        SaleItem(
                            product = product,
                            onSelectItem = {},
                            onIncrementProductToCar = {
                                onIncrementProductToCar?.invoke(product)
                            },
                            onSubtractProductToCar = {
                                onSubtractProductToCar?.invoke(product)
                            }
                        )
                    }

                }

            }

            Spacer(Modifier.weight(1f))

        }
        if (totalSale != null && totalSale > 0) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = AnnotatedString.fromHtml(
                            stringResource(
                                R.string.total_to_pay,
                                totalSale ?: 0
                            )
                        ),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,

                        )
                    Text(
                        text = "|",
                        fontSize = 18.sp,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Text(
                        text = stringResource(R.string.btn_pay),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .clickable {

                            },
                        textAlign = TextAlign.Right,
                        color = RedDark,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable()
fun SalesViewPreview() {
    ReportsGoTheme {
        SalesView(totalSale = 34.toDouble())
    }
}
