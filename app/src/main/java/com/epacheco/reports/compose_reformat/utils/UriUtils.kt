package com.epacheco.reports.compose_reformat.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.facebook.BuildConfig
import java.io.OutputStream


object UriUtils {
    fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        val contentResolver = context.contentResolver
        val filename =
            "${BuildConfig.APPLICATION_ID + BuildConfig.BUILD_TYPE + System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ProfilePictures")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val uri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            var outputStream: OutputStream? = null
            try {
                outputStream = contentResolver.openOutputStream(it)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                outputStream?.close()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, contentValues, null, null)
            }
        }
        return uri
    }

}