package com.epacheco.reports.compose_reformat.utils.extensions


import java.text.NumberFormat
import java.util.Locale

fun Double.toCurrencyFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(this)
}