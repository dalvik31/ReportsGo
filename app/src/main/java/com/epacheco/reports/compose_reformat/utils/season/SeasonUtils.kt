package com.epacheco.reports.compose_reformat.utils.season

import java.util.Calendar


object SeasonUtils {
     fun getSeason(): Season {
        val calendar = Calendar.getInstance()
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        return if ((month == 2 && day >= 19) || (month == 8 && day <= 23) || (month in 3..7)) {
            Season.SPRING
        } else {
            Season.FALL
        }
    }
}