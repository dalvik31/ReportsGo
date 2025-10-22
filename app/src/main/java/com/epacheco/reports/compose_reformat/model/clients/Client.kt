package com.epacheco.reports.compose_reformat.model.clients

import com.epacheco.reports.Pojo.ClientDetail.ClientDetail
import com.epacheco.reports.compose_reformat.model.orders.Order


data class Client(
    val id: String = "",
    val name: String = "",
    val lastNanme: String = "",
    val detail: String = "",
    val phone: String = "",
    val debt: Double= 0.0,
    val limit: Double = 0.0,
    val limitUsed: Double = 0.0,
    var dateClient: String = "",
    val clientsDetails: HashMap<String, ClientDetailCmps>? = null
) {
    fun geProgressLimit(): Float {
        var limitCredit = 0f
        if (limitUsed > 0) {
            limitCredit = (limitUsed / limit).toFloat()
        }
        return limitCredit
    }

    fun getLimitAvailable(): Double {
        return limit - limitUsed
    }
}
