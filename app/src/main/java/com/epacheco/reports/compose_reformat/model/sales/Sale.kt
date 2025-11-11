package com.epacheco.reports.compose_reformat.model.sales

import androidx.annotation.Keep
import com.epacheco.reports.compose_reformat.model.Finances.PaymentType


data class Sale(
    val saleId: String = "",
    val imgProduct: String = "",
    val productPriceBuy: Double = 0.0,
    val productPriceSale: Double = 0.0,
    val productName: String = "",
    val productId: String = "",
    val idClient: String = "",
    val nameClient: String = "",
    val auxStock: Int = 0,
    val isCancelSale: Boolean = false,
    val saleDate: String = "",
    val paymentType: PaymentType = PaymentType.UNKNOWN,
    val saleConcept: String = "",
) {
    @Keep
    constructor() : this("", "", 0.0, 0.0, "", "", "", "", 0, false, "", PaymentType.UNKNOWN, "")

}