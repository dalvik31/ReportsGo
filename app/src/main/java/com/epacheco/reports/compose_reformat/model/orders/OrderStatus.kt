package com.epacheco.reports.compose_reformat.model.orders

import androidx.annotation.Keep
import com.epacheco.reports.R
import kotlinx.serialization.Serializable


@Keep
@Serializable
enum class OrderStatus(val orderStatusName: Int) {
    IN_PROGRESS(R.string.tab_in_progress),
    DONE(R.string.tab_done)
}