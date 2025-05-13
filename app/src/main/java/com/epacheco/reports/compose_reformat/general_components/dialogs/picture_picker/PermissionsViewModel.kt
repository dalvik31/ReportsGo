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
    private val manifestGalleryPermission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

    private val manifestCameraPermission = Manifest.permission.CAMERA

    private val manifestArrayPermission =
        arrayOf(manifestGalleryPermission, manifestCameraPermission)

    fun galleryPermissionIsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            app,
            manifestGalleryPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun cameraPermissionIsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            app,
            manifestCameraPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getUriToFile(uri: Uri): File {
        return uri.toFile(app)
    }

    fun getBitmapToFile(imageBitmap: Bitmap): File {
        return imageBitmap.toFile(app)
    }

    fun getGalleryPermission() = manifestGalleryPermission

    fun getArrayPermissions() = manifestArrayPermission
}