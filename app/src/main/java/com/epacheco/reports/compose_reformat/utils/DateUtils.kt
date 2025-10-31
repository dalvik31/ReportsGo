package com.epacheco.reports.compose_reformat.utils

import android.util.Log
import com.epacheco.reports.tools.Tools
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object DateUtils {

    fun format(date: Date, format: String) = apply(date, format)

    fun dateFormat(timestamp: String, format: String): String {
        val cal = Calendar.getInstance()
        cal.setTimeInMillis(timestamp.toLong())
        val date = Date(cal.timeInMillis)
        val sdf = SimpleDateFormat(format, Locale("es"))
        sdf.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().toString())
        Log.e("vamoos", "fechaaaaaa: ${date.toString()}")
        val format = sdf.format(date)
        Log.e("vamoos", "fechaaaaaa2: ${format.toString()}")
        return format
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