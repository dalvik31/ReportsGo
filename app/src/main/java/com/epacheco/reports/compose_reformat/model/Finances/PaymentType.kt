package com.epacheco.reports.compose_reformat.model.Finances

import androidx.annotation.Keep

@Keep
enum class PaymentType {
    CASH,
    CREDIT,
    PAY,
    UNKNOWN
}