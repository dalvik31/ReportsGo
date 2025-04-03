package com.epacheco.reports.compose_reformat.model.products

import android.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productDate: String = "",
    val productId: String = "",
    val urlImage: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productType: String = "",
    val productPriceBuy: Double = 0.0,
    val productPriceSale: Double = 0.0,
    val productCode: String = "",
    val inStock: Int = 0,
    val auxPrice: Double = 0.0,
    val auxStock: Int = 0,
    val talla: String = "",
    val color: String = "",
    val colorProductCode: String? = null,
    val tipo_de_empaque: String = "",
    val especificaciones_otro: String = "",
    val typeProduct: String = "",
)
