package com.epacheco.reports.compose_reformat.model.products.supabase

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName

data class ProductDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("image")
    val image: String?,
    @SerialName("color")
    val color: String?,
    @SerialName("stock")
    val stock: Int?,
    @SerialName("code")
    val code: String?,
    @SerialName("date")
    val date: Instant,
    @SerialName("description")
    val description: String,
    @SerialName("pricebuy")
    val priceBuy: Float,
    @SerialName("type")
    val type: String,
    @SerialName("size")
    val size: String

)
