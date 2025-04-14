package com.epacheco.reports.compose_reformat.model.orders


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Order(
    var orderListId: String = "",
    val orderId: String = "",
    val orderName: String = "",
    val orderSize: String = "",
    val orderColor: String = "",
    val orderColorCode: String? = null,
    val orderGender: String = "",
    val orderDescription: String = "",
    val orderSizeNumeric: Boolean = false,
    val orderBuy: Boolean = false,
    var orderSeason: Season? = null,
    // val orderProduct: Product = Product()
) : Parcelable