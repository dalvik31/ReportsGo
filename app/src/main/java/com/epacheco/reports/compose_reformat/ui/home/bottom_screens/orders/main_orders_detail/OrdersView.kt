package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.OrderItem
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE4
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersView(
    orderList: List<Order> = emptyList(),
    showImgEmptyList: Boolean? = null,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    onOrderClick: ((Order?) -> Unit)? = null,
    onCreateOrderClick: (() -> Unit)? = null,
    onEditOrderClick: ((Order) -> Unit)? = null,
    moveSelectedItems: ((List<Order>) -> Unit)? = null,
    onUpdateStatusOrderClick: ((Order) -> Unit)? = null,
    nameOrderMain: String? = null,
    mainOrderId: String? = null,
    progressList: Float? = null,
    isSelectedMode: Boolean = false
) {

    val selectedItems = remember { mutableStateListOf<Order>() }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {

        Column(Modifier.background(color = Color.Transparent)) {

            Header(
                text = if (isSelectedMode) "Selección" else pluralStringResource(
                    R.plurals.title_orders,
                    count = orderList.size,
                    orderList.size
                ),
                textColor = MaterialTheme.colorScheme.primary,
                onLeftIconClicked = { if (!isSelectedMode) onBackPressed?.invoke() },
                leftImageVector = Icons.Default.ArrowBackIosNew,
                tintImageLeft = if (isSelectedMode) Color.Transparent else MaterialTheme.colorScheme.primary,
                onRightIconClicked = {
                    if (isSelectedMode) {
                        selectedItems.clear()
                        onOrderClick?.invoke(null)
                    } else onCreateOrderClick?.invoke()
                },
                tintImageRight = MaterialTheme.colorScheme.primary,
                rightImageVector = ImageVector.vectorResource(if (isSelectedMode) R.drawable.ic_error else R.drawable.ic_vector_add),
            )
            nameOrderMain?.let { nameOrder ->
                TextDivider(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 16.dp),
                    text = nameOrder.ifEmpty {
                        DateUtils.dateFormat(
                            mainOrderId.toString(),
                            FORMAT_DATE4
                        )
                    }
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LinearProgressIndicator(
                    progress = { progressList ?: 0f },
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .padding(end = 16.dp),
                    drawStopIndicator = {}
                )

                val progress = (progressList ?: 0f) * 100
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = "${progress.toInt()} %",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }


            Spacer(modifier = Modifier.padding(all = 8.dp))
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { onRefresh?.invoke() },
            ) {
                if (showImgEmptyList == true) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_vector_empty_orders),
                            contentDescription = null,

                            )
                        Text(
                            color = MaterialTheme.colorScheme.primary,
                            text = stringResource(R.string.list_orders_empty),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        itemsIndexed(orderList) { order, index ->

                            val isSelected = selectedItems.contains(index)

                            OrderItem(
                                order = orderList[order],
                                onOrderClick = {
                                    onUpdateStatusOrderClick?.invoke(orderList[order])
                                },
                                onOrderLongClick = {
                                    if (isSelected) selectedItems.remove(index)
                                    else selectedItems.add(index)
                                    onOrderClick?.invoke(orderList[order])
                                },
                                isModeSelected = isSelectedMode,
                                onEditOrderClick = {
                                    onEditOrderClick?.invoke(it)
                                },
                                isSelectedItem = isSelected,
                                onOrderCheckedClick = {
                                    if (isSelected) selectedItems.remove(index)
                                    else selectedItems.add(index)
                                }
                            )
                        }
                    }
                }

            }

        }

        if (isSelectedMode) {
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
                        text = "${selectedItems.size} Pedidos",
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
                        text = "Mover",
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .clickable {
                                moveSelectedItems?.invoke(selectedItems)
                            },
                        textAlign = TextAlign.Right,
                        color = GreenColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun OrderMainViewPreview() {
    ReportsGoTheme {
        OrdersView(showImgEmptyList = true, nameOrderMain = "Mi lista", isSelectedMode = true)
    }
}