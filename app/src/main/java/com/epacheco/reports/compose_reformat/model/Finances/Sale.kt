package com.epacheco.reports.compose_reformat.model.Finances

data class Sale(
    val saleId: String = "",
    val imgProduct: String = "",
    val productPricreBuy: Double = 0.0,
    val productPriceSale: Double = 0.0,
    val productName: String = "",
    val productId: String = "",
    val idClient: Double = 0.0,
    val nameClient: String = "",
    val auxStock: Int = 0,
    val isCancelSale: Boolean = false,
    val saleDate: String = "",
    val isCreditSale: Boolean = false,
)
