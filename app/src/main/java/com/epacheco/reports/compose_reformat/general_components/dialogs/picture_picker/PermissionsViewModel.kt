package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.utils.extensions.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor(private val app: ReportsApp) : ViewModel() {
    fun getUriToFile(uri: Uri): File {
        return uri.toFile(app)
    }

    fun getBitmapToFile(imageBitmap: Bitmap): File {
        return imageBitmap.toFile(app)
    }

}