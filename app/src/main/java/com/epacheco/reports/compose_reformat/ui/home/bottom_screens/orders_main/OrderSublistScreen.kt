package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OrderSublistScreen(orderMainId: String) {
    Box(Modifier.fillMaxSize()) {
        Text("OrderSublistScreen ${orderMainId}")
    }
}