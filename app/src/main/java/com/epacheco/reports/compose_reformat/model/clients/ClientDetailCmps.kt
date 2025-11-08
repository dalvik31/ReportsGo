package com.epacheco.reports.compose_reformat.model.clients

data class ClientDetailCmps(
    var datePayment: String = "",
    var amount: Double = 0.0,
    var concept: String = "",
    var isPay: Boolean = false,
    var debt: Double = 0.0,
    var cantProduct: Int = 0,
    var urlImage: String = "",
    var productId: String = "",
    var updateStock: Int = 0,
    var productName: String = "",
    var productPriceBuy: Double = 0.0,
    var productPriceSale: Double = 0.0,
    var auxStock: Int = 0,
    var isCreditSale: Boolean = false
)
