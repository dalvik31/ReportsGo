package com.epacheco.reports.compose_reformat.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


object DateUtils {

    fun format(date: Date, format: String) = apply(date, format)

    fun format(timestamp: Long, format: String) = apply(Date(timestamp), format)

    fun now() = Calendar.getInstance().time

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
        format, Locale.getDefault()
    ).format(date)

    fun formatTransferDate(date: Date): String =
        format(date, FORMAT_DATE1) + "T00"

    fun parseServerDate(date: String) = parse(date, FORMAT_DATE1)

    fun formatLimitDate(date: Date): String =
        format(date, FORMAT_DATE2)

    fun formatPixCodeDate(date: Date): String = format(date, FORMAT_DATE8)


    const val FORMAT_DATE1 = "yyyy-MM-dd"
    const val FORMAT_DATE2 = "dd/MM/yyyy"
    const val FORMAT_DATE3 = "dd / MMMM / YYYY"
    const val FORMAT_DATE7 = "dd MMM, YYYY"
    const val FORMAT_DATE8 = "dd MMM. YYYY"



}