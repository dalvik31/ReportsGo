package com.epacheco.reports.compose_reformat.utils

import android.graphics.Color
import com.epacheco.reports.compose_reformat.model.orders.Season
import kotlin.random.Random


object ColorUtils {
    fun getColor(): Int {
        val rnd: Random = Random
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return color
    }
}