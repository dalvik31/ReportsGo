package com.epacheco.reports.compose_reformat.utils.extensions


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import com.epacheco.reports.compose_reformat.ui.theme.Green
import com.epacheco.reports.compose_reformat.ui.theme.Yellow

fun Color.toHexString(): String {
    return String.format(
        "#%02x%02x%02x%02x", (this.alpha * 255).toInt(),
        (this.red * 255).toInt(), (this.green * 255).toInt(), (this.blue * 255).toInt()
    )
}