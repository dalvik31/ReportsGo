package com.epacheco.reports.compose_reformat.model.products

import androidx.annotation.Keep

@Keep
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
    val productSize: String = "",
    val productSizeNumeric: Boolean = false,
    val productColor: String = "",
    val productColorCode: String = "",
    val inStock: Int = 0,
    val auxPrice: Double = 0.0,
    var auxStock: Int = 0,
    val talla: String = "",
    val color: String = "",
    val tipo_de_empaque: String = "",
    val especificaciones_otro: String = "",
    val typeProduct: String = "",
) {

    @Keep
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        0.0,
        0.0,
        "",
        "",
        false,
        "",
        "",
        0,
        0.0,
        0,
        "",
        "",
        "",
        "",
        ""
    )

    fun getSize(): String {
        return talla.ifEmpty {
            productSize.ifEmpty {
                ""
            }
        }
    }
}
