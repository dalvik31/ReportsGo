package com.epacheco.reports.compose_reformat.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.FallColor
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.SpringColor
import com.epacheco.reports.compose_reformat.ui.theme.YellowColor

object Utils {
    @Composable
    fun getCardBackground(orderMain: OrderMain): Color =
        when (orderMain.orderSeason) {
            Season.FALL -> FallColor
            Season.SPRING -> SpringColor
            null -> MaterialTheme.colorScheme.onBackground
        }


    @Composable
    fun getClientDotBackground(limitCreditPercent: Float): Color =
        when (limitCreditPercent) {
            in 0.0001f..0.4f -> GreenColor
            in 0.4f..0.8f -> YellowColor
            in 0.8f..1f -> RedDark
            else -> MaterialTheme.colorScheme.onBackground
        }

    @Composable
    fun getClientBalanceColor(limitCreditPercent: Float): Color =
        when (limitCreditPercent) {
            0.0f -> MaterialTheme.colorScheme.primary
            in 0.000001f..0.4f -> MaterialTheme.colorScheme.primary
            in 0.4f..0.8f -> YellowColor
            in 0.8f..1f -> RedDark
            else -> RedDark
        }

}