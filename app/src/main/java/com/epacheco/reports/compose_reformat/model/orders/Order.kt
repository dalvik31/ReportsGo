package com.epacheco.reports.compose_reformat.model.orders


import android.os.Parcelable
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import kotlinx.parcelize.Parcelize

import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Order(
    @Serializable
    var orderListId: String = "",
    @Serializable
    val orderId: String = "",
    @Serializable
    val nameOrder: String = "",
    @Serializable
    val orderSize: String = "",
    @Serializable
    val orderColor: String = "",
    @Serializable
    val orderColorCode: String? = null,
    @Serializable
    val orderGender: String = "",
    @Serializable
    val orderDescription: String = "",
    @Serializable
    val orderSizeNumeric: Boolean = false,
    @Serializable
    val orderStatus: OrderStatus = OrderStatus.IN_PROGRESS,
    @Serializable
    var orderSeason: Season? = null,
   // val orderProduct: Product = Product()
) : Parcelable