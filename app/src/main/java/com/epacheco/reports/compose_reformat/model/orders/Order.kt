package com.epacheco.reports.compose_reformat.model.orders


import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
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
    val orderClientName: String? = null,
    val orderClientId: String? = null,
    val locationLat: Double? = null,
    val locationLong: Double? = null,
    val address: String? = null
) {

    @Keep
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        false,
        false,
        null,
        null,
        null,
        locationLat = 0.0,
        locationLong = 0.0,
        address = ""
    )

    fun getOrderOptions(): List<String> {
        val orderOptionsList = arrayListOf<String>()
        orderSize.isNotEmpty().let {
            orderOptionsList.add(orderSize)
        }
        orderColor.isNotEmpty().let {
            orderOptionsList.add(orderColor)
        }
        orderGender.isNotEmpty().let {
            orderOptionsList.add(orderGender)
        }
        return orderOptionsList
    }
}