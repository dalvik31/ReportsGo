package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun MoneyItem(
    modifier: Modifier = Modifier,
    text: String? = null,
    amount: String? = null,
    icon: Int? = null,
    isAmount: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    tintIcon: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: (() -> Unit)? = null
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentWidth().height(IntrinsicSize.Min)
    ) {

        IconButton(
            {
                onClick?.invoke()
            }, modifier = modifier
                .wrapContentWidth()

                .background(
                    backgroundColor,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon ?: R.drawable.baseline_circle_24),
                tint = tintIcon,
                contentDescription = "",
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(all = 8.dp)

            )
        }

        val moneySymbol = if (isAmount) "$" else ""

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            text?.let {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = text,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                )
            }

            amount?.let {
                Text(
                    modifier = modifier.padding(vertical = 4.dp),
                    text = "${moneySymbol}${amount ?: 0.0}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }

        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MoneyItemPreview() {
    ReportsGoTheme {
        MoneyItem(text = "Venta", icon = R.drawable.baseline_circle_24)
    }
}