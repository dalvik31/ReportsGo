package com.epacheco.reports.compose_reformat.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object DateUtils {

    fun format(date: Date, format: String) = apply(date, format)

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

    fun dateFormat(timestamp: Long, format: String): String {
        val zoneFormatDate = convertMillisToLocalDate(timestamp)
        val dateFormatter = DateTimeFormatter.ofPattern(format, Locale.getDefault())
        return dateFormatter.format(zoneFormatDate)
    }

    fun format(timestamp: Long, format: String) = apply(Date(timestamp), format)

    fun now() = Calendar.getInstance().time.time

    fun parse(timestamp: Long) = Date(timestamp)

    fun parse(
        source: String,
        format: String
    ) = SimpleDateFormat(
        format,
        Locale.getDefault()
    ).parse(source)

    fun formatDatePicker(date: Date): String {
        val res = SimpleDateFormat("dd MMM, yyyy", Locale("pt")).format(date)
        res.indexOfFirst { it.isLetter() }.let {
            if (it != 1) return res.substring(0, it) + res.substring(it, it + 1)
                .uppercase() + res.substring(it + 1, res.length)
        }
        return res
    }

    private fun apply(
        date: Date,
        format: String
    ): String = SimpleDateFormat(
        format, Locale("es")
    ).format(date)


    const val FORMAT_DATE1 = "dd / MMMM / YYYY"
    const val FORMAT_DATE2 = "dd/MMMM/yy"
    const val FORMAT_DATE3 = "dd / MMMM / YYYY - HH:MM"
    const val FORMAT_DATE4 = "dd MMM, YYYY"
    const val FORMAT_DATE5 = "dd/MM/yy"
    const val FORMAT_DATE6 = "EEEE"


}