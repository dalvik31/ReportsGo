package com.epacheco.reports.compose_reformat.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


object DateUtils {

    fun convertMillisToLocalDate(millis: Long): ZonedDateTime {
        val utcDateAtStartOfDay = Instant
            .ofEpochMilli(millis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
        val localDate = utcDateAtStartOfDay.atStartOfDay(ZoneId.systemDefault())
        return localDate

    }


    fun dateFormat(timestamp: String, format: String): String {
        val zoneFormatDate = convertMillisToLocalDate(timestamp.toLong())
        val dateFormatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return dateFormatter.format(zoneFormatDate)
    }

    fun now() = Calendar.getInstance().time.time


    const val FORMAT_DATE1 = "dd / MMMM / YYYY"
    const val FORMAT_DATE2 = "dd/MMMM/yy"
    const val FORMAT_DATE3 = "dd/MMM/YYYY"
    const val FORMAT_DATE4 = "dd/MM/yy"
    const val FORMAT_DATE5 = "d MMM yy"
    const val FORMAT_DATE6 = "dd MMM yyyy hh:mm a"


}