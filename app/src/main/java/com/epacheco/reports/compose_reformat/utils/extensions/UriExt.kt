@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.epacheco.reports.compose_reformat.utils.extensions

import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.toFile(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(this)
    val tempFile = File.createTempFile("temp", ".jpg")
    tempFile.outputStream().use { fileOut ->
        inputStream?.copyTo(fileOut)
    }
    tempFile.deleteOnExit()
    inputStream?.close()
    return tempFile
}


