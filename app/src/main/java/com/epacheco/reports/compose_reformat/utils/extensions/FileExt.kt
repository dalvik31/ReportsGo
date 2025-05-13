package com.epacheco.reports.compose_reformat.utils.extensions

import android.content.Context
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import java.io.File

suspend fun File.compress(context: Context): File {
    return Compressor.compress(context, this) {
        resolution(100, 100)
        quality(75)
    }
}


