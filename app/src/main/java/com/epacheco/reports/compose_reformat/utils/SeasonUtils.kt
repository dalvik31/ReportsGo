package com.epacheco.reports.compose_reformat.utils

import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.orders.Season.FALL
import com.epacheco.reports.compose_reformat.model.orders.Season.SPRING
import java.util.Calendar


object SeasonUtils {
    fun getSeason(): Season {
        val calendar = Calendar.getInstance()
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        return if ((month == 2 && day >= 19) || (month == 8 && day <= 23) || (month in 3..7)) {
            SPRING
        } else {
            FALL
        }
    }
}