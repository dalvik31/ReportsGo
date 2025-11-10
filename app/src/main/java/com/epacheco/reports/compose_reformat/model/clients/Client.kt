package com.epacheco.reports.compose_reformat.model.clients


data class Client(
    val id: String = "",
    val name: String = "",
    val lastNanme: String = "",
    val detail: String = "",
    val phone: String = "",
    val debt: Double = 0.0,
    val limit: Double = 0.0,
    var dateClient: String = "",
) {
    fun geProgressLimit(): Float {
        var limitCredit = 0f
        if (debt > 0) {
            limitCredit = (debt / limit).toFloat()
        }
        return limitCredit
    }

    fun getLimitAvailable(): Double {
        return limit - debt
    }
}
