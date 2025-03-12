package com.epacheco.reports.compose_reformat.utils.extensions


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import com.epacheco.reports.compose_reformat.ui.theme.Green
import com.epacheco.reports.compose_reformat.ui.theme.Yellow


fun Int.StockColor(): Color {
    return when (this) {
        0 -> Gray
        in 1..5 -> Red
        in 5..10 -> Yellow
        in 10..Int.MAX_VALUE -> Green
        else -> Gray
    }
}