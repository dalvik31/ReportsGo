package com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.utils.extensions.toFile
import dagger.hilt.android.lifecycle.HiltViewModel
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
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