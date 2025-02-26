package com.epacheco.reports.compose_reformat.model.clients

import com.epacheco.reports.Pojo.ClientDetail.ClientDetail

data class Client(
    val id: String = "",
    val Name: String = "",
    val lastNanme: String = "",
    val detail: String = "",
    val phone: String = "",
    val limit: Double = 0.0,
    val dateClient: String = "",
    val ClientsDetails: HashMap<String, ClientDetail>? = null
)
