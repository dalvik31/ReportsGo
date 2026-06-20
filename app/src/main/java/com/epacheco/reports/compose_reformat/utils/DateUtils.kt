package com.epacheco.reports.compose_reformat.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
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


    fun stringDateToLong(dateString: String, format: String): Long {
        val formatter = DateTimeFormatter.ofPattern(format)
        val temporal = formatter.parse(dateString)
        val localDate = LocalDate.from(temporal)
        val localTime = if (temporal.isSupported(ChronoField.HOUR_OF_DAY)) {
            LocalTime.from(temporal)
        } else {
            LocalTime.MIDNIGHT
        }
        return ZonedDateTime.of(localDate, localTime, ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun dateFormat(timestamp: String, format: String, numDays: Long = 0): String {
        var zoneFormatDate = convertMillisToLocalDate(timestamp.toLong())
        if (numDays > 0) {
            zoneFormatDate = zoneFormatDate.plusDays(numDays)
        }
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
    const val FORMAT_DATE7 = "EEEE, dd MMMM YYYY"
    const val FORMAT_DATE8 = "W"


}