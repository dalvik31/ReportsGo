package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE3


@Composable
fun OrderMainItem(
    order: Order,
    onMainOrderClick: (Order) -> Unit,
    onDeleteOrderClick: (String) -> Unit

) {

    var showDialogConfirmDeleteOrder by remember { mutableStateOf(false) }

    Surface(color = Color.Transparent) {
        Card(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clickable {
                    onMainOrderClick.invoke(order)
                }
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .alpha(.2f)
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.imagen_otonio_3),
                    contentDescription = null,
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )

                ) {
                    Column(Modifier.fillMaxSize(), Arrangement.SpaceBetween) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                text = order.nameOrder.uppercase(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ), fontSize = 14.sp
                            )

                            IconButton({
                                showDialogConfirmDeleteOrder = true
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_vector_remove),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                                .align(Alignment.End),
                            text = DateUtils.format(order.dateOrder.toLong(), FORMAT_DATE3),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }
            }
        }

    }


    if (showDialogConfirmDeleteOrder) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_order_title),
            dialogSubTitle = stringResource(R.string.msg_delete_order_title, order.nameOrder),
            confirmButtonText = stringResource(R.string.btn_ok),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteOrder = false },
            onConfirmation = {
                showDialogConfirmDeleteOrder = false
                onDeleteOrderClick.invoke(order.dateOrder)
            }
        )
    }

}


@Preview
@Composable
fun OrderMainItemPreview() {
    OrderMainItem(
        Order(dateOrder = "0", nameOrder = "name"),
        onMainOrderClick = {},
        onDeleteOrderClick = {})
}
