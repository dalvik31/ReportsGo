package com.epacheco.reports.compose_reformat.model.sales

data class SaleDetail(
    val datePayment: String = "",
    val amount: Double = 0.0,
    val concept: String = "",
    val isPay: Boolean = false,
    val dept: Double = 0.0,
    val cantProduct: Int = 0,
    val urlImage: String = "",
    val productId: String = "",
    val updateStock: Int = 0,
    val productName: String = "",
    val productPriceBuy: Double = 0.0,
    val productPriceSale: Double = 0.0,
    val auxStock: Int = 0,
    val isCreditSale: Boolean = false
)
