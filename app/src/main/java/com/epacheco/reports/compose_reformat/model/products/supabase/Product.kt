package com.epacheco.reports.compose_reformat.model.products.supabase

import kotlinx.datetime.Instant

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val image: String?,
    val color: String?,
    val stock: Int?,
    val code: String?,
    val date: Instant,
    val description: String,
    val priceBuy: Float,
    val type: String,
    val size: String
)
